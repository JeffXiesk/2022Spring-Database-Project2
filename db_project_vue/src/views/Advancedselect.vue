<template>
  <div>
    <h1 align="center">advanced query of order</h1>
    <div>
      <h3>Please input the contract number</h3>
      <el-input v-model="input1" placeholder="Please input the contract number" style="width: 50%"/>
    </div>
    <div>
      <h3>Please input the enterprise</h3>
      <el-input v-model="input2" placeholder="Please input the enterprise" style="width: 50%"/>
    </div>
    <div>
      <h3>Please input the product_model</h3>
      <el-input v-model="input3" placeholder="Please input the product_model" style="width: 50%"/>
    </div>
    <div>
      <h3>Please input the manager name</h3>
      <el-input v-model="input4" placeholder="Please input the manager name" style="width: 50%"/>
    </div>
    <div>
      <h3>Please input the salesman name</h3>
      <el-input v-model="input5" placeholder="Please input the salesman name" style="width: 50%"/>
    </div>
    <div>
      <h2></h2>
      <el-button type="success" @click="advancequery">Search</el-button>
    </div>
    <h2>Result</h2>
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
  </div>
</template>

<script>
import axios from 'axios';

export default {
  methods: {
    advancequery (){
      const _this = this;
      const url = 'http://localhost:8181/allorder/advancequery?c_n='+_this.input1+'&enterprise='+_this.input2+'&p_m='+_this.input3+'&manager='+_this.input4+'&salesman='+_this.input5
      axios.get(url).then(res=>{
        _this.tableData = res.data;
      })
    }
  },
  created() {
    const _this = this;
    console.log(_this.type)
  },
  data() {
    return {
      activeIndex: '1',
      tableData: null,
      input1: '',
      input2: '',
      input3: '',
      input4: '',
      input5: ''
    }
  }
}
</script>

<style>
.table_vi {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  width: 80%;
}

</style>