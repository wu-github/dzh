<template>
  <div class="common-layout home-container">
    <el-container>
      <el-header>
        <div>
          <h5>home</h5>
        </div>
        <div class="home-container-link">
          <router-link to="/three-demo">three-demo</router-link>
          <router-link to="/three-kali">three-kali</router-link>
          <router-link to="/three-ammo">three-ammo</router-link>
        </div>
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
