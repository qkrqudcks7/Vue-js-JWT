<template>
  <div id="MyInfo">
    <header class="jumbotron">
      <h3>username : {{content.username}}</h3>
      <h3>email : {{content.email}}</h3>
      <h3>id : {{content.id}}</h3>
      <h3>password : {{content.password}}</h3>
    </header>
  </div>
</template>

<script>
import UserService from '../service/user-service'

export default {
  name: 'MyInfo',
  data () {
    return {
      content: ''
    }
  },
  mounted () {
    UserService.getUser()
      .then(response => {
        console.log(response.data)
        this.content = response.data
      },
      error => {
        this.content = error.response.data.message
      })
  }
}
</script>

<style scoped>

</style>
