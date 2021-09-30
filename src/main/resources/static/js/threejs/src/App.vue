<template>
  <div class="common-layout app-container">
    <el-container>
      <el-header>
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
      </el-header>
    </el-container>
  </div>
</template>

<script lang="ts">
import { ref, defineComponent } from "vue";
import constants from "./constants/constants.js";

export default defineComponent({
  setup() {
    const defaultSelect = constants.examples.length
      ? constants.examples[0].title
      : "";
    return {
      defaultSelect: defaultSelect,
      options: ref(constants.examples),
      value: ref(defaultSelect),
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
        constants.examples.forEach((element) => {
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