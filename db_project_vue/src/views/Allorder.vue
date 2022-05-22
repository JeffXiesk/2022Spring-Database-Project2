<template>
  <div class="about">
    <h1>This is the order table</h1>
  </div>

  <div class="table_vi">
    <el-table :data="tableData" stripe table-layout="fixed" align="center">
      <el-table-column prop="contract_num" label="Contract Num" align="center"/>
      <el-table-column prop="enterprise" label="Enterprise" align="center"/>
      <el-table-column prop="product_model" label="Product Model" align="center"/>
      <el-table-column prop="quantity" label="Quantity" align="center"/>
      <el-table-column prop="contract_manager" label="Contract Manager" align="center"/>
      <el-table-column prop="contract_date" label="Contract Date" align="center"/>
      <el-table-column prop="estimated_delivery_date" label="Estimated Date" align="center"/>
      <el-table-column prop="lodgement_date" label="Lodgement Date" align="center"/>
      <el-table-column prop="salesman_num" label="Salesman Num" align="center"/>
      <el-table-column prop="contract_type" label="Contract Type" align="center"/>
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
      var url = 'http://localhost:8181/allorder/findAll/'+currentPage+'/10'
      axios.get(url).then(function (resp) {
        _this.tableData = resp.data.content;
        _this.total = resp.data.totalElements;
      })
    }
  },
  created(currenPage){
    const _this = this;
    axios.get('http://localhost:8181/allorder/findAll/1/10').then(function (resp) {
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