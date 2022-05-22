<template>
  <el-container class="layout-container-demo" style="height: 1000px">
    <el-aside width="200px" style="background-color: aliceblue">
      <el-scrollbar>
        <el-menu :default-active="activeIndex"
                 @select="handleselect"
        >
          <el-menu-item index="1">getAllStaffCount</el-menu-item>
          <el-menu-item index="2">getContractCount</el-menu-item>
          <el-menu-item index="3">getOrderCount</el-menu-item>
          <el-menu-item index="4">getNeverSoldProductCount</el-menu-item>
          <el-menu-item index="5">getFavoriteProductModel</el-menu-item>
          <el-menu-item index="6">getAvgStockByCenter</el-menu-item>
          <el-menu-item index="7">getProductByNumber</el-menu-item>
          <el-menu-item index="8">getContractInfo</el-menu-item>
        </el-menu>
      </el-scrollbar>
    </el-aside>

    <el-container>
      <el-main>
        <div v-if="type==='1'">
          <h1>getAllStaffCount</h1>
          <el-table :data="tableData" stripe table-layout="fixed" align="center">
            <el-table-column property="staff" label="staff" align="center"/>
            <el-table-column property="count" label="count" align="center"/>
          </el-table>
        </div>
        <div v-else-if="type==='2'">
          <h1 align="center">getContractCount</h1>
          <h2>
            Contract number count is {{ tableData }}.
          </h2>
        </div>
        <div v-else-if="type==='3'">
          <h1 align="center">getOrderCount</h1>
          <h2>
            Order count is {{ tableData }}.
          </h2>
        </div>
        <div v-else-if="type==='4'">
          <h1 align="center">getNeverSoldProductCount</h1>
          <h2>
            The never sold product count is {{ tableData }}.
          </h2>
        </div>
        <div v-else-if="type==='5'">
          <h1 align="center">getFavoriteProductModel</h1>
          <h2>
            The favourite product model is {{ tableData.at(0) }},
            and it sold {{ tableData.at(1) }}.
          </h2>
        </div>
        <div v-else-if="type==='6'">
          <h1 align="center">getAvgStockByCenter</h1>
          <el-table :data="tableData" stripe table-layout="fixed" align="center">
            <el-table-column property="supply_center" label="Supply Center" align="center"/>
            <el-table-column property="average" label="Average" align="center"/>
          </el-table>
        </div>
        <div v-else-if="type==='7'">
          <h1 align="center">getProductByNumber</h1>
          <h2>Please input the number</h2>
          <div>
            <el-input v-model="input" placeholder="Please input the number" style="width: 50%"/>
            <el-button type="success" @click="getProductByNumber">Search</el-button>
          </div>
          <h2>Result</h2>
          <el-table :data="tableData" stripe table-layout="fixed" align="center">
            <el-table-column property="supply_center" label="Supply Center" align="center"/>
            <el-table-column property="product_model" label="Product Model" align="center"/>
            <el-table-column property="purchase_price" label="Purchase Price" align="center"/>
            <el-table-column property="quantity" label="Quantity" align="center"/>
          </el-table>
        </div>
        <div v-else-if="type==='8'">
          <h1 align="center">getContractInfo</h1>
          <h2>Please input the contract number</h2>
          <div>
            <el-input v-model="input" placeholder="Please input the contract number" style="width: 50%"/>
            <el-button type="success" @click="getContractInfo">Search</el-button>
          </div>
          <h2>Result</h2>
          <el-table :data="tableData" stripe table-layout="fixed" align="center">
            <el-table-column property="contract_number" label="Contract Number" align="center"/>
            <el-table-column property="enterprise" label="Enterprise" align="center"/>
            <el-table-column property="manager" label="Manager" align="center"/>
            <el-table-column property="supply_center" label="Supply Center" align="center"/>
          </el-table>
          <el-table :data="tableData2" stripe table-layout="fixed" align="center">
            <el-table-column property="product_model" label="Product Model " align="center"/>
            <el-table-column property="salesman" label="Salesman" align="center"/>
            <el-table-column property="quantity" label="Quantity" align="center"/>
            <el-table-column property="unit_price" label="Unit Price" align="center"/>
            <el-table-column property="estimate_delivery_date" label="Estimate Delivery Date" align="center"/>
            <el-table-column property="lodgement_date" label="Lodgement Date" align="center"/>
          </el-table>
        </div>
        <div v-else>
          <h2>Please choose an operation</h2>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import axios from 'axios';

export default {
  methods: {
    handleselect(key, value) {
      const _this = this;
      _this.type = key;
      _this.tableData = null
      if (_this.type === '1') {
        axios.get('http://localhost:8181/staff/getAllStaffCount').then(function (res) {
          _this.tableData = [];
          for (let i = 0; i < res.data.length; i = i + 1) {
            _this.tableData.push({staff: res.data.at(i)[0], count: res.data.at(i)[1]})
          }
        })
      }
      if (_this.type === '2') {
        axios.get('http://localhost:8181/contract/getContractCount').then(function (res) {
          _this.tableData = res.data
        })
      }
      if (_this.type === '3') {
        axios.get('http://localhost:8181/allorder/getOrderCount').then(function (res) {
          _this.tableData = res.data
        })
      }
      if (_this.type === '4') {
        axios.get('http://localhost:8181/product/getNeverSoldProductCount').then(function (res) {
          _this.tableData = res.data
        })
      }
      if (_this.type === '5') {
        axios.get('http://localhost:8181/product/getFavoriteProductModel').then(function (res) {
          _this.tableData = res.data
          console.log(res.data)
        })
      }
      if (_this.type === '6') {
        axios.get('http://localhost:8181/product/getAvgStockByCenter').then(function (res) {
          _this.tableData = [];
          for (let i = 0; i < res.data.length; i = i + 1) {
            _this.tableData.push({supply_center: res.data.at(i)[0], average: res.data.at(i)[1]})
          }
        })
      }
      if (_this.type === '7') {
      }
      if (_this.type === '8') {
      }
    },
    getProductByNumber (){
      const _this = this;
      const url = 'http://localhost:8181/product/getProductByNumber?num='+_this.input;
      console.log(url);
      axios.get(url).then(function (res) {
        _this.tableData = [];
        for (let i = 0; i < res.data.length; i = i + 1) {
          _this.tableData.push({
            supply_center: res.data.at(i)[0],
            product_model: res.data.at(i)[1],
            purchase_price: res.data.at(i)[2],
            quantity: res.data.at(i)[3]
          })
        }
      })
    },getContractInfo (){
      const _this = this;
      const url = 'http://localhost:8181/contract/getContractInfo1?contract_num='+_this.input;
      axios.get(url).then(function (res) {
        _this.tableData = [];
        for (let i = 0; i < res.data.length; i = i + 1) {
          _this.tableData.push({
            contract_number: _this.input,
            enterprise: res.data.at(i)[0],
            manager: res.data.at(i)[1],
            supply_center: res.data.at(i)[2],
          })
        }
      })
      const url2 = 'http://localhost:8181/contract/getContractInfo2?contract_num='+_this.input;
      axios.get(url2).then(function (res) {
        _this.tableData2 = [];
        for (let i = 0; i < res.data.length; i = i + 1) {
          _this.tableData2.push({
            product_model: res.data.at(i)[0],
            salesman: res.data.at(i)[1],
            quantity: res.data.at(i)[2],
            unit_price: res.data.at(i)[3],
            estimate_delivery_date: res.data.at(i)[4],
            lodgement_date: res.data.at(i)[5]
          })
        }
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
      type: 0,
      tableData: null,
      tableData2: null,
      input: ''
    }
  }
}

</script>

<style scoped>
.layout-container-demo .el-header {
  position: relative;
  background-color: var(--el-color-primary-light-7);
  color: var(--el-text-color-primary);
}

.layout-container-demo .el-aside {
  color: var(--el-text-color-primary);
  background: var(--el-color-primary-light-8);
}

.layout-container-demo .el-menu {
  border-right: none;
}

.layout-container-demo .el-main {
  padding: 0;
}

.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}
</style>