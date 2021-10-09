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
import register from "./register/register.js";

export default defineComponent({
  setup() {
    const defaultSelect = register.examples.length
      ? register.examples[0].title
      : "";
    return {
      defaultSelect: defaultSelect,
      options: ref(register.examples),
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