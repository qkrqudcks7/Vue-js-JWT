<template>
<div id="ModifyBoard">
  <div class="container" style="margin-top:100px">
    <div class="row">
      <div class="col-sm-3"></div>
      <div class="col-sm-6">
        <div class="card shadow">
          <div class="card-body">
              <div class="form-group">
                <label for="writer">작성자</label>
                <input type="text" id="writer" v-model="myBoard.writer" class="form-control" disabled="disabled"/>
              </div>
              <div class="form-group">
                <label for="localDateTime">작성날짜</label>
                <input type="text" id="localDateTime" v-model="myBoard.localDateTime" class="form-control" disabled="disabled"/>
              </div>
              <div class="form-group">
                <label for="subject">제목</label>
                <input type="text" v-model="myBoard.subject" id="subject"  class="form-control"/>
              </div>
              <div class="form-group">
                <label for="text">내용</label>
                <textarea v-model="myBoard.text" id="text" class="form-control" rows="10" style="resize:none"></textarea>
              </div>
              <div class="form-group">
                <div class="text-right">
                  <button @click="modifyBoard" class="btn btn-primary">수정완료</button>
                  <button @click="BoardList" class="btn btn-info">취소</button>
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

export default {
  name: 'ModifyBoard',
  data () {
    return {
      myBoard: {}
    }
  },
  computed: {
    boardId () {
      return this.$route.params.id
    },
    loginUser () {
      if (this.$store.state.auth.user) {
        return this.$store.state.auth.user
      } else {
        return ''
      }
    }
  },
  methods: {
    BoardList () {
      this.$router.push('boardlist')
    },
    modifyBoard () {
      axios.put(`http://localhost:8080/api/board/getoneboard`, this.myBoard)
        .then(() => {
          this.$router.push({name: 'getoneboard', params: {id: this.boardId}})
        })
    }
  },
  created () {
    axios.get(`http://localhost:8080/api/board/getoneboard/${this.boardId}`)
      .then(result => {
        this.myBoard = result.data
        console.log(this.myBoard)
      })
  }
}
</script>

<style scoped>

</style>
