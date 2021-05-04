<template>
<div id="GetOneBoard">
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
              <input type="text" v-model="myBoard.subject" id="subject"  class="form-control" disabled="disabled"/>
            </div>
            <div class="form-group">
              <label for="text">내용</label>
              <textarea v-model="myBoard.text" id="text" class="form-control" rows="10" style="resize:none" disabled="disabled"></textarea>
            </div>
            <div class="form-group">
              <div class="text-right">
                <button class="btn btn-primary" @click="BoardList">목록으로</button>
                <span v-if="myBoard.writer === this.loginUser.username"><button class="btn btn-info" @click="ModifyBoard">수정하기</button>
                  <button class="btn btn-danger" @click="DeleteBoard">삭제하기</button></span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <table class = "table table-striped">
        <thead>
        <tr>
          <th>작성자</th>
          <th>내용</th>
          <th>작성시간</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(i,index) in commentList" :key="index">
          <td>{{i.writer}}</td>
          <td>{{i.comment}}</td>
          <td>{{i.localDateTime}}</td>
        </tr>
        </tbody>
      </table>
      <div class="form-signing">
        <h2><label for="comment" class = "label label-default">댓글입력</label></h2>
        <textarea id="comment" v-model="commentForm.comment" class="form-control" placeholder="내용을 입력해주세요" required></textarea>
        <br>
        <button class = "btn btn-info" type = "submit" @click="submitComment">등록하기</button>
      </div>
    </div>
  </div>

</div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'GetOneBoard',
  data () {
    return {
      myBoard: {},
      commentList: [],
      commentForm: {
        id: '',
        comment: ''
      }
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
    ModifyBoard () {
      this.$router.push({name: 'modifyboard', params: {id: this.boardId}})
    },
    DeleteBoard () {
      axios.delete(`http://localhost:8080/api/board/getoneboard/${this.boardId}`)
        .then(() => {
          this.$router.push('boardlist')
        })
    },
    submitComment () {
      this.commentForm.id = this.loginUser.id
      axios.post(`http://localhost:8080/api/board/comments/${this.boardId}`, this.commentForm)
        .then(() => {
          this.commentForm.comment = ''
          axios.get(`http://localhost:8080/api/board/comments/${this.boardId}`)
            .then(result => {
              this.commentList = result.data
            })
        })
        .catch(error => {
          console.log(error)
        })
    }
  },
  created () {
    axios.get(`http://localhost:8080/api/board/getoneboard/${this.boardId}`)
      .then(result => {
        this.myBoard = result.data
      })
    axios.get(`http://localhost:8080/api/board/comments/${this.boardId}`)
      .then(result => {
        this.commentList = result.data
      })
  }
}
</script>

<style scoped>

</style>
