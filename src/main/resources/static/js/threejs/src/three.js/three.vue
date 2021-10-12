<template>
  <div class="common-layout index-container">
    <el-container>
      <el-header>
        <div>
          <img class="index-container-select-prefix" :src="chPng" />
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
      defaultSelect,
      options,
      value,
      chPng: require("../../public/image/demo/ch.png"),
    };
  },
  mounted() {
    this.change(this.defaultSelect);
  },
  methods: {
    change(value) {
      const view = document.getElementById("three-view");
      view.innerHTML = "";
      if (value) {
        register.examples.forEach((element) => {
          if (element.title == value) {
            const v = new element.view();
            v.render();
          }
        });
      }
    },
  },
});
</script>