<template>
  <div id="backcont">
    <el-menu
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
    >
      <el-menu-item index="1">Home</el-menu-item>
      <el-menu-item index="7">Load Initial Data</el-menu-item>
      <el-sub-menu index="3">
        <template #title>Initialize</template>
        <el-menu-item index="3-1">center</el-menu-item>
        <el-menu-item index="3-2">enterprise</el-menu-item>
        <el-menu-item index="3-3">model</el-menu-item>
        <el-menu-item index="3-4">staff</el-menu-item>
      </el-sub-menu>
      <el-menu-item index="4">Stock In</el-menu-item>
      <el-menu-item index="8">Inventory</el-menu-item>
      <el-sub-menu index="5">
        <template #title>Modify Order</template>
        <el-menu-item index="5-1">PlaceOrder</el-menu-item>
        <el-menu-item index="5-2">UpdateOrder</el-menu-item>
        <el-menu-item index="5-3">DeleteOrder</el-menu-item>
      </el-sub-menu>
      <el-menu-item index="9">Show Order</el-menu-item>
      <el-menu-item index="10">Show Contract</el-menu-item>
      <el-menu-item index="6">Get Information</el-menu-item>
      <el-menu-item index="11">Advanced Select</el-menu-item>
      <el-menu-item index="12">Clear All</el-menu-item>
    </el-menu>
    <router-view/>
  </div>
</template>

<script>
import {ref} from 'vue'
import {useRouter} from "vue-router"
import axios from 'axios'

export default {
  setup() {
    const router = useRouter();
    const activeIndex = ref('1')
    const handleSelect = (key, value) => {
      console.log('Key is :' + key)
      console.log('value is :' + value)
      if (key === '1')
        router.push('/')
      if (key === '2')
        router.push('/about')
      if (key === '7') {
        axios.post('http://localhost:8181/center/init')
        axios.post('http://localhost:8181/enterprise/init')
        axios.post('http://localhost:8181/model/init')
        axios.post('http://localhost:8181/staff/init')
        alert("Load initial data successfully !")
      }
      if (key === '3-1') {
        router.push('/center')
      }
      if (key === '3-2') {
        router.push('/enterprise')
      }
      if (key === '3-3') {
        router.push('/model')
      }
      if (key === '3-4') {
        router.push('/staff')
      }
      if (key === '4') {
        axios.post('http://localhost:8181/product/stockIn')
        alert("Stock in data successfully !")
      }
      if (key === '8') {
        router.push('/product')
      }

      if (key === '5-1') {
        axios.post('http://localhost:8181/allorder/placeOrder').then(res=>{
          axios.post('http://localhost:8181/contract/add/contractnum')
        })
        alert('Place order successfully!')
      }
      if (key === '5-2') {
        axios.post('http://localhost:8181/allorder/updateOrder')
        alert('Update order successfully!')
      }
      if (key === '5-3') {
        axios.post('http://localhost:8181/allorder/deleteOrder')
        alert('Delete order successfully!')
      }
      if (key === '9') {
        router.push('/order')
      }
      if (key === '10') {
        router.push('/contract')
      }
      if (key === '6') {
        router.push('/getinfo')
      }
      if (key === '11') {
        router.push('/advancedselect')
      }
      if (key === '12') {
        axios.post('http://localhost:8181/allrepo/clearall').then(res=>{
          alert('Clear all data successfully !')
        })
      }
    }
    return {activeIndex, handleSelect}
  }
}
</script>


<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
