import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import CenterView from '../views/CenterView'
import EnterpriseView from "../views/EnterpriseView";
import ModelView from "../views/ModelView";
import StaffView from "../views/StaffView";
import ProductView from "../views/ProductView";
import AllorderView from "../views/Allorder";
import ContractView from "../views/ContractView";
import getinfo from "../views/Getinfo"
import advancedselectView from "../views/Advancedselect"

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/center',
    name: 'center',
    component: CenterView
  },
  {
    path: '/enterprise',
    name: 'enterprise',
    component: EnterpriseView
  },
  {
    path: '/model',
    name: 'model',
    component: ModelView
  },
  {
    path: '/staff',
    name: 'staff',
    component: StaffView
  },
  {
    path: '/product',
    name: 'product',
    component: ProductView
  },
  {
    path: '/order',
    name: 'order',
    component: AllorderView
  },
  {
    path: '/contract',
    name: 'contract',
    component: ContractView
  },
  {
    path: '/getinfo',
    name: 'getinfo',
    component: getinfo
  },
  {
    path: '/advancedselect',
    name: 'advancedselect',
    component: advancedselectView
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
