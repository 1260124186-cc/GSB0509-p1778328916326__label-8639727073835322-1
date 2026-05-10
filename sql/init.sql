SET NAMES utf8mb4;
CREATE DATABASE IF NOT EXISTS smart_elderly DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE smart_elderly;

-- 用户表
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'admin',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 老人表
CREATE TABLE IF NOT EXISTS elderly (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    age INT,
    gender VARCHAR(10),
    phone VARCHAR(20),
    address VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 健康记录表
CREATE TABLE IF NOT EXISTS health_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    elderly_id INT NOT NULL,
    blood_pressure VARCHAR(20),
    temperature DECIMAL(4, 2),
    is_abnormal BOOLEAN DEFAULT FALSE,
    check_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (elderly_id) REFERENCES elderly(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 初始数据
INSERT INTO users (username, password, role) VALUES ('admin', '123456', 'admin');

INSERT INTO elderly (name, age, gender, phone, address) VALUES 
('张大爷', 75, '男', '13800138001', '朝阳区幸福路1号'),
('王奶奶', 82, '女', '13800138002', '海淀区安康里10号'),
('李爷爷', 68, '男', '13800138003', '西城区平顺街5号');

INSERT INTO health_records (elderly_id, blood_pressure, temperature, is_abnormal, check_time) VALUES 
(1, '120/80', 36.5, FALSE, NOW()),
(1, '130/85', 37.5, TRUE, DATE_SUB(NOW(), INTERVAL 1 HOUR)),
(2, '118/75', 36.8, FALSE, NOW()),
(3, '140/90', 36.2, FALSE, NOW());
