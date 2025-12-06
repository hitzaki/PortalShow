<template>
  <div class="dashboard-container">
    <el-header class="header">
      <div class="logo">PortalShow Admin</div>
      <el-button type="danger" size="small" @click="handleLogout">退出登录</el-button>
    </el-header>
    
    <el-main class="main-content">
      <el-card>
        <template #header>
          <div class="card-header">
            <span>客户咨询列表</span>
            <el-button type="primary" size="small" @click="refreshData">刷新数据</el-button>
          </div>
        </template>
        
        <el-table :data="tableData" stripe style="width: 100%" v-loading="loading">
          <el-table-column prop="createTime" label="提交时间" width="180" />
          <el-table-column prop="name" label="客户姓名" width="120" />
          <el-table-column prop="phone" label="联系电话" width="150" />
          <el-table-column prop="email" label="电子邮箱" width="200" />
          <el-table-column prop="message" label="需求描述" />
          <el-table-column label="状态" width="100">
            <template #default="scope">
              <el-tag :type="scope.row.status === '已处理' ? 'success' : 'warning'">{{ scope.row.status }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="150">
            <template #default="scope">
              <el-button size="small" @click="handleView(scope.row)">查看</el-button>
              <el-button size="small" type="success" v-if="scope.row.status === '待处理'" @click="handleComplete(scope.row)">标记处理</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-main>

    <!-- 详情弹窗 -->
    <el-dialog v-model="dialogVisible" title="咨询详情" width="500px">
      <el-descriptions :column="1" border>
        <el-descriptions-item label="姓名">{{ currentItem.name }}</el-descriptions-item>
        <el-descriptions-item label="电话">{{ currentItem.phone }}</el-descriptions-item>
        <el-descriptions-item label="邮箱">{{ currentItem.email }}</el-descriptions-item>
        <el-descriptions-item label="提交时间">{{ currentItem.createTime }}</el-descriptions-item>
        <el-descriptions-item label="需求描述">{{ currentItem.message }}</el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import request from '@/utils/request'

const router = useRouter()
const loading = ref(false)
const dialogVisible = ref(false)
const currentItem = ref({})
const tableData = ref([])

const refreshData = async () => {
  loading.value = true
  try {
    const res = await request.post('/contact/list')
    if (res.code === 0) {
      tableData.value = res.data
    }
  } catch (error) {
    // handled
  } finally {
    loading.value = false
  }
}

const handleLogout = () => {
  localStorage.removeItem('admin_token')
  router.push('/login')
}

const handleView = (row) => {
  currentItem.value = row
  dialogVisible.value = true
}

const handleComplete = async (row) => {
  try {
    const res = await request.post('/contact/complete', row)
    if (res.code === 0) {
      ElMessage.success('已标记为已处理')
      row.status = '已处理'
    }
  } catch (e) {
    // handled
  }
}

onMounted(() => {
  refreshData()
})
</script>

<style scoped>
.dashboard-container {
  min-height: 100vh;
}

.header {
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  height: 60px;
}

.logo {
  font-size: 20px;
  font-weight: bold;
  color: #409EFF;
}

.main-content {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
