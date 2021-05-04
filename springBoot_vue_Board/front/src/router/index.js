import Vue from 'vue'
import Router from 'vue-router'
import Register from '../components/Register'
import Home from '../components/Home'
import Login from '../components/Login'
import Profile from '../components/Profile'
import store from '../store'
import MyInfo from '../components/MyInfo'
import BoardList from '../components/BoardList'
import writeBoard from '../components/writeBoard'
import GetOneBoard from '../components/GetOneBoard'
import ModifyBoard from '../components/ModifyBoard'

Vue.use(Router)

const requireAuth = () => (to, from, next) => {
  if (store.state.auth.user) {
    return next()
  }
  next('/login')
}

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/register',
      name: 'register',
      component: Register
    },
    {
      path: '/profile',
      name: 'profile',
      component: Profile,
      beforeEnter: requireAuth()
    },
    {
      path: '/myinfo',
      name: 'myinfo',
      component: MyInfo,
      beforeEnter: requireAuth()
    },
    {
      path: '/boardlist',
      name: 'boardlist',
      component: BoardList
    },
    {
      path: '/writeboard',
      name: 'writeboard',
      component: writeBoard,
      beforeEnter: requireAuth()
    },
    {
      path: '/getoneboard',
      name: 'getoneboard',
      component: GetOneBoard
    },
    {
      path: '/modifyboard',
      name: 'modifyboard',
      component: ModifyBoard,
      beforeEnter: requireAuth()
    }
  ]
})
