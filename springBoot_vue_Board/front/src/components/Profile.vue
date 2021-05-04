<template>
  <div class="container">
    <header class="jumbotron">
      <h3>username : {{content.username}}</h3>
      <h3>email : {{content.email}}</h3>
    </header>
  </div>
</template>

<script>
import UserService from '../service/user-service'

export default {
  name: 'Profile',
  data () {
    return {
      content: ''
    }
  },
  mounted () {
    UserService.getUserContent()
      .then(response => {
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
