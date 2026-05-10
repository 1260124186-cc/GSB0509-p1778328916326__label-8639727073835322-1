<template>
  <div class="health-container">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>录入健康数据</span>
            </div>
          </template>
          <el-form :model="healthForm" label-width="80px">
            <el-form-item label="老人">
              <el-select v-model="healthForm.elderlyId" placeholder="选择老人" style="width: 100%">
                <el-option v-for="item in elderlyList" :key="item.id" :label="item.name" :value="item.id" />
              </el-select>
            </el-form-item>
            <el-form-item label="血压">
              <el-input v-model="healthForm.bloodPressure" placeholder="如: 120/80" />
            </el-form-item>
            <el-form-item label="体温">
              <el-input-number v-model="healthForm.temperature" :precision="1" :step="0.1" :min="30" :max="45" style="width: 100%" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSave" style="width: 100%">保存数据</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>历史健康记录</span>
            </div>
          </template>
          <el-table :data="historyData" border style="width: 100%" v-loading="loading">
            <el-table-column prop="elderlyName" label="姓名" />
            <el-table-column prop="bloodPressure" label="血压" />
            <el-table-column prop="temperature" label="体温">
                <template #default="scope">
                    <span :class="{ 'abnormal': scope.row.temperature > 37.3 }">
                        {{ scope.row.temperature }} ℃
                    </span>
                    <el-tag v-if="scope.row.isAbnormal" type="danger" size="small" style="margin-left: 10px">异常</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="checkTime" label="检测时间" width="180" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../utils/request'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const elderlyList = ref([])
const historyData = ref([])
const healthForm = ref({
  elderlyId: null,
  bloodPressure: '',
  temperature: 36.5
})

const loadElderly = async () => {
  const res = await request.get('/elderly/list')
  elderlyList.ref = res.data
  elderlyList.value = res.data
}

const loadHistory = async () => {
  loading.value = true
  try {
    const res = await request.get('/health/history')
    historyData.value = res.data
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadElderly()
  loadHistory()
})

const handleSave = async () => {
  if (!healthForm.value.elderlyId) {
    ElMessage.warning('请选择老人')
    return
  }
  await request.post('/health/add', healthForm.value)
  if (healthForm.value.temperature > 37.3) {
      ElMessage.error('警告：体温异常！已自动标记。')
  } else {
      ElMessage.success('保存成功')
  }
  loadHistory()
}
</script>

<style scoped>
.abnormal {
  color: #F56C6C;
  font-weight: bold;
}
</style>
