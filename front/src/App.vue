<!--<script setup lang="ts">-->
<!--import {RouterLink, RouterView} from 'vue-router'-->
<!--import Header from "@/components/Header.vue";-->
<!--import Content from "@/components/Content.vue";-->
<!--</script>-->

<!--<template>-->

<!--&lt;!&ndash;  <el-header class="header">&ndash;&gt;-->
<!--&lt;!&ndash;    <el-menu mode="horizontal">&ndash;&gt;-->
<!--&lt;!&ndash;      <el-menu-item index="1">Home</el-menu-item>&ndash;&gt;-->
<!--&lt;!&ndash;      <el-menu-item index="2">글 작성하기</el-menu-item>&ndash;&gt;-->
<!--&lt;!&ndash;    </el-menu>&ndash;&gt;-->
<!--&lt;!&ndash;  </el-header>&ndash;&gt;-->
<!--  <Header/>-->


<!--&lt;!&ndash;  <Content/>&ndash;&gt;-->


<!--  <RouterView />-->


<!--</template>-->

<!--<style>-->

<!--body {-->
<!--  margin : 0;-->
<!--/*  width: 100%;*/-->
<!--/*  font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,*/-->
<!--/*  "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR",*/-->
<!--/*  "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",*/-->
<!--/*  sans-serif;*/-->
<!--}-->
<!--</style>-->

<script setup lang="ts">
import Header from "@/components/Header.vue";
import Content from "@/components/Content.vue";
</script>

<template>
  <Header />
  <Content />
</template>

<style>
body {
  font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
  "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR",
  "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
  sans-serif;
}
</style>
