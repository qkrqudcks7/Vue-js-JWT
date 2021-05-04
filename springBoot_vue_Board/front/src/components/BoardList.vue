<template>
<div id="BoardList">
  <div class="container" style="margin-top:100px">
    <div class="card shadow">
      <div class="card-body">
        <h4 class="card-title">Board</h4>
        <table class="table table-hover" id='board_list'>
          <thead>
          <tr>
            <th class="text-center d-none d-md-table-cell">글번호</th>
            <th class="w-50">제목</th>
            <th class="text-center d-none d-md-table-cell">작성자</th>
            <th class="text-center d-none d-md-table-cell">조회수</th>
            <th class="text-center d-none d-md-table-cell">작성날짜</th>
          </tr>
          </thead>
          <tbody>
            <tr v-for="(i,index) in boardList" :key="index">
              <td class="text-center d-none d-md-table-cell">{{i.id}}</td>
              <td class="text-center d-none d-md-table-cell" @click="getOneBoard(i.id)">{{i.subject}}</td>
              <td class="text-center d-none d-md-table-cell">{{i.writer}}</td>
              <td class="text-center d-none d-md-table-cell">{{i.viewCount}}</td>
              <td class="text-center d-none d-md-table-cell">{{i.localDateTime}}</td>
            </tr>
          </tbody>
        </table>

        <div class="text-right">
          <button class="btn btn-primary" @click="writeBoard">글쓰기</button>
        </div>

      </div>
    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'BoardList',
  data () {
    return {
      boardList: {}
    }
  },
  created () {
    axios.get('http://localhost:8080/api/board/getall')
      .then(response => {
        this.boardList = response.data.data
      })
  },
  methods: {
    writeBoard () {
      this.$router.push({name: 'writeboard'})
    },
    getOneBoard (id) {
      this.$router.push({name: 'getoneboard', params: {id: id}})
    }
  }
}
</script>

<style scoped>

</style>
