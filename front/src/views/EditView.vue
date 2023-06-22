<script setup lang="ts">

import {ref} from "vue";
import {useRouter} from "vue-router";
import axios from "axios";

const router = useRouter();

const props = defineProps({
  postId: {
    type: [Number, String],
    required: true,
  }
})

const post = ref({
  postId: {
    type: [Number, String],
    required: true
  }
});

axios.get(`/api/posts/${props.postId}`)
    .then((response) => {
      post.value = response.data;
    })

const edit = () => {
  axios.patch(`/api/posts/${props.postId}`, post.value)
      .then((response) => {
        router.replace( {name: "home"} )
      })
}


</script>


<template>

  <div class="mt-2">
    <el-input v-model="post.title"/>
  </div>

  <div class="mt-2">
    <el-input v-model="post.content" type="textarea" rows="15"/>
  </div>

  <el-button type="warning" @click="edit()"> 글 수정 완료</el-button>
</template>


<style lang="scss" scoped>

</style>