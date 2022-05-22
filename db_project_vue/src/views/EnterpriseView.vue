<template>
  <div class="about">
    <h1>This is the enterprise table</h1>
  </div>

  <div class="table_vi">
    <el-table :data="tableData" stripe table-layout="fixed" align="center">
      <el-table-column prop="name" label="Name" align="center"/>
      <el-table-column prop="country" label="Country" align="center"/>
      <el-table-column prop="supply_center" label="Supply Center" align="center"/>
      <el-table-column prop="industry" label="Industry" align="center"/>
    </el-table>
    <el-pagination background layout="prev, pager, next" :total="total" @click="handleClick" @current-change="page" />
  </div>

</template>

<script>
import axios from 'axios'

export default {
  methods:{
    handleClick(row) {
      console.log(row);
    },
    page(currentPage){
      const _this = this;
      var url = 'http://localhost:8181/enterprise/findAll/'+currentPage+'/10'
      axios.get(url).then(function (resp) {
        _this.tableData = resp.data.content;
        _this.total = resp.data.totalElements;
      })
    }
  },
  created(currenPage){
    const _this = this;
    axios.get('http://localhost:8181/enterprise/findAll/1/10').then(function (resp) {
      _this.tableData = resp.data.content;
      _this.total = resp.data.totalElements;
    })
  },
  data() {
    return {
      total: null,
      tableData: null
    }
  }
}
</script>

<style>
.table_vi {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  width: 70%;
}

label {
  font-weight: bold;
  display: block;
  margin-bottom: 10px;
}

#backcont {
  background-image: url("../img/background.jpg");
  background-attachment: fixed;
  background-repeat: no-repeat;
  background-size: cover;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  min-height: 100vh;
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

</style>