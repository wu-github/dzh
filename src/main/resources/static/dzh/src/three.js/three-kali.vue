<template>
  <div class="common-layout three-container">
    <el-container>
      <el-header>
        <div>
          <span class="three-container-link">
            <router-link to="/home">home</router-link>
          </span>
        </div>
      </el-header>
    </el-container>
  </div>
  <div id="three-view"></div>
</template>
<script type="module">
import { ref, defineComponent } from "vue";
import * as THREE from "three/build/three.module";
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls.js";
import { MMDLoader } from "three/examples/jsm/loaders/MMDLoader.js";
import { OutlineEffect } from "three/examples/jsm/effects/OutlineEffect.js";
import Stats from "three/examples/jsm/libs/stats.module.js";
import { MMDAnimationHelper } from "three/examples/jsm/animation/MMDAnimationHelper.js";
export default defineComponent({
  setup() {
    return {
      renderer: null,
      scene: null,
      camera: null,
      effect: null,
      stats: null,
      helper: null,
      clock: null,
      animateId: null,
      resources: [],
    };
  },
  mounted() {
    this.render();
  },
  unmounted() {
    this.dispose();
  },
  methods: {
    render() {
      const ele = document.getElementById("three-view");
      if (typeof Ammo == "function") {
        let that = this;
        Ammo().then(function (AmmoLib) {
          Ammo = AmmoLib;
          that.init(ele);
          that.animate();
        });
      } else {
        ele.innerHTML = "";
        this.init(ele);
        this.animate();
      }

      window.addEventListener("resize", this.onWindowResize);
    },
    init(ele) {
      let that = this;
      const clock = (this.clock = new THREE.Clock());
      this.resources.push(clock);
      let camera = (this.camera = new THREE.PerspectiveCamera(
        45,
        window.innerWidth / window.innerHeight,
        1,
        2000
      ));
      camera.position.z = 30;

      let scene = (this.scene = new THREE.Scene());
      scene.background = new THREE.Color(0xffffff);

      const gridHelper = new THREE.PolarGridHelper(30, 10);
      gridHelper.position.y = -10;
      scene.add(gridHelper);
      this.resources.push(gridHelper);
      const ambient = new THREE.AmbientLight(0x666666);
      scene.add(ambient);
      this.resources.push(ambient);
      const directionalLight = new THREE.DirectionalLight(0x887766);
      directionalLight.position.set(-1, 1, 1).normalize();
      scene.add(directionalLight);
      this.resources.push(directionalLight);
      let renderer = (this.renderer = new THREE.WebGLRenderer({
        antialias: true,
      }));
      renderer.setPixelRatio(window.devicePixelRatio);
      renderer.setSize(window.innerWidth, window.innerHeight);
      ele.appendChild(renderer.domElement);

      let effect = (this.effect = new OutlineEffect(renderer));
      this.resources.push(effect);
      let stats = (this.stats = new Stats());
      this.resources.push(stats);
      ele.appendChild(stats.dom);

      function onProgress(xhr) {
        if (xhr.lengthComputable) {
          const percentComplete = (xhr.loaded / xhr.total) * 100;
          console.log(Math.round(percentComplete, 2) + "% downloaded");
        }
      }

      const modelFile = "./public/module/Kali/Kali.pmx";
      const vmdFiles = [];

      let helper = (this.helper = new MMDAnimationHelper({
        afterglow: 2.0,
      }));

      const loader = new MMDLoader();
      this.resources.push(loader);

      loader.loadWithAnimation(
        modelFile,
        vmdFiles,
        function (mmd) {
          let mesh = mmd.mesh;
          scene.add(mesh);

          helper.add(mesh, {
            animation: mmd.animation,
            physics: true,
          });

          let ikHelper = helper.objects.get(mesh).ikSolver.createHelper();
          ikHelper.visible = false;
          scene.add(ikHelper);
          that.resources.push(ikHelper);

          let physicsHelper = helper.objects.get(mesh).physics.createHelper();
          physicsHelper.visible = false;
          scene.add(physicsHelper);
          that.resources.push(physicsHelper);
        },
        onProgress,
        null
      );

      const controls = new OrbitControls(camera, renderer.domElement);
      controls.minDistance = 10;
      controls.maxDistance = 100;
    },
    animate() {
      let that = this;
      function a() {
        that.animateId = requestAnimationFrame(a);
        that.stats.begin();
        that.helper.update(that.clock.getDelta());
        that.effect.render(that.scene, that.camera);
        that.stats.end();
      }
      a();
    },
    onWindowResize() {
      this.camera.aspect = window.innerWidth / window.innerHeight;
      this.camera.updateProjectionMatrix();

      this.renderer.setSize(window.innerWidth, window.innerHeight);
    },
    dispose() {
      try {
        this.scene.clear();
        this.camera.clear();
        this.renderer.dispose();
        this.renderer.forceContextLoss();
        this.renderer.content = null;
        cancelAnimationFrame(this.animateId);
        let gl = this.renderer.domElement.getContext("webgl");
        gl && gl.getExtension("WEBGL_lose_context").loseContext();
        for (let i = 0; i < this.resources.length; i++) {
          this.resources[i].dispose && this.resources[i].dispose();
          this.resources[i].clear && this.resources[i].clear();
        }
        console.debug(this.renderer.info);
      } catch (e) {
        console.error(e);
      }
    },
  },
});
</script>