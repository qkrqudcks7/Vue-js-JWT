<template>
<div id="writeBoard">
  <div class="container" style="margin-top:100px">
    <div class="row">
      <div class="col-sm-3"></div>
      <div class="col-sm-6">
        <div class="card shadow">
          <div class="card-body">
              <div class="form-group">
                <label for="writer">작성자</label>
                <input type="text" id="writer" class="form-control" v-model="currentUser" disabled="disabled"/>
              </div>
              <div class="form-group">
                <label for="localDateTime">작성날짜</label>
                <input type="text" id="localDateTime" v-model="localDateTime"  disabled="disabled"/>
              </div>
              <div class="form-group">
                <label for="subject">제목</label>
                <input type="text" id="subject" class="form-control" v-model="boardRequest.subject"/>
              </div>
              <div class="form-group">
                <label for="text">내용</label>
                <textarea id="text" class="form-control" rows="10" style="resize:none" v-model="boardRequest.text"></textarea>
              </div>
              <div class="form-group">
                <div class="text-right">
                  <button @click="submitBoard()" class="btn btn-primary">작성하기</button>
                </div>
              </div>
          </div>
        </div>
      </div>
      <div class="col-sm-3"></div>
    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios'
import authHeader from '../service/auth-header'

export default {
  name: 'writeBoard',
  data () {
    return {
      boardRequest: {subject: '', text: ''}
    }
  },
  computed: {
    currentUser () {
      return this.$store.state.auth.user.username
    },
    localDateTime () {
      let today = new Date()
      return today.toLocaleString()
    }
  },
  methods: {
    submitBoard () {
      axios.post('http://localhost:8080/api/board/addboard', this.boardRequest, {headers: authHeader()})
        .then(response => {
          if (response.status === 200) {
            alert('등록 되었습니다.')
            this.$router.push('boardList')
          } else {
            alert('등록에 실패했습니다.')
          }
        })
    }
  }
}
</script>

<style scoped>

</style>
