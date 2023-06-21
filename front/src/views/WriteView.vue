<script setup lang="ts">

import {ref} from "vue";

import axios from "axios";

const title = ref("")
const content = ref("")

const write = function () {
  axios.post("/api/posts", {
    title: title.value,
    content: content.value
  })
}

</script>


<template>

  <div class="mt-2">
    <el-input v-model="title" placeholder="제목을 입력해주세요"/>
  </div>

  <div class="mt-2">
    <el-input v-model="content" type="textarea" rows="15"/>
  </div>

  <el-button type="danger" @click="write()"> 글 작성 완료</el-button>
</template>


<style lang="scss" scoped>

</style>