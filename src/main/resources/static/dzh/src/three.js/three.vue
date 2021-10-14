<template>
  <div class="common-layout three-container">
    <el-container>
      <el-header>
        <div>
          <span class="three-container-link">
            <router-link to="/home">home</router-link>
          </span>
          <span>
            <img class="three-container-select-prefix" :src="chPng" />
            <el-select
              v-model="value"
              clearable
              placeholder="Select"
              @change="change"
            >
              <el-option
                v-for="item in options"
                :key="item.title"
                :label="item.title"
                :value="item.title"
              >
              </el-option>
            </el-select>
          </span>
        </div>
      </el-header>
    </el-container>
  </div>
  <div id="three-view"></div>
</template>

<script lang="ts">
import { WEBGL } from "three/examples/jsm/WebGL";
import { ref, defineComponent } from "vue";
import register from "../three.js/register/register.js";
import "../css/three.css";
const chPng = require("../image/demo/ch.png");

export default defineComponent({
  setup() {
    let defaultSelect;
    let options;
    let value;
    if (WEBGL.isWebGLAvailable()) {
      console.log("Welcome");
      defaultSelect = register.examples.length
        ? register.examples[0].title
        : "";
      options = ref(register.examples);
      value = ref(defaultSelect);
    } else {
      const warning = WEBGL.getWebGLErrorMessage();
      document.getElementById("container").appendChild(warning);
    }
    return {
      chPng,
      defaultSelect,
      options,
      value,
      currentView: null,
    };
  },
  mounted() {
    this.change(this.defaultSelect);
  },
  unmounted() {
    this.dispose();
  },
  methods: {
    change(value) {
      const view = document.getElementById("three-view");
      view.innerHTML = "";
      if (value) {
        this.dispose();
        register.examples.forEach((element) => {
          if (element.title == value) {
            this.currentView = new element.view();
            this.currentView.render(view);
          }
        });
      }
    },
    dispose() {
      this.currentView && this.currentView.dispose();
    },
  },
});
</script>