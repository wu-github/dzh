<template>
  <div class="common-layout home-container">
    <el-container>
      <el-header>
        <div>
          <h5>home</h5>
        </div>
        <router-link to="/three">three</router-link>
      </el-header>
    </el-container>
  </div>
</template>
<script lang="ts">
import { ref, defineComponent } from "vue";
import "../css/home.css";

export default defineComponent({
  setup() {},
});
</script>
