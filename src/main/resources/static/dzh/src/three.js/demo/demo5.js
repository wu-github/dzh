import * as THREE from 'three/build/three.module'
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls.js";
import { MMDLoader } from "three/examples/jsm/loaders/MMDLoader.js";

// import { MMDParser } from 'three/examples/jsm/libs/mmdparser.module.js';
// import { MMDPhysics } from 'three/examples/jsm/animation/MMDPhysics.js';
// import { CCDIKSolver } from 'three/examples/jsm/animation/CCDIKSolver.js';
// import { TGALoader } from 'three/examples/jsm/loaders/TGALoader.js';
// import { MMDToonShader } from 'three/examples/jsm/shaders/MMDToonShader.js';

import { OutlineEffect } from "three/examples/jsm/effects/OutlineEffect.js";
import Stats from "three/examples/jsm/libs/stats.module.js";
import { GUI } from 'three/examples/jsm/libs/dat.gui.module.js';
import { MMDAnimationHelper } from "three/examples/jsm/animation/MMDAnimationHelper.js";
import register from '../register/register.js';
import { demo } from './demo';

class demo5 extends demo {
    constructor() {
        super();
        this.renderer;
        this.scene;
        this.camera;
        this.effect;
        this.stats;
        this.helper;
        this.physicsHelper;
        this.gui;
        this.clock;
        this.ikHelper;
        this.animateId;
        this.resources = [];
    }

    render(ele) {
        let that = this;
        if (typeof Ammo == "function") {
            Ammo().then(function(AmmoLib) {
                Ammo = AmmoLib;
                that.init(ele);
                that.animate();
            });
        } else {
            ele.innerHTML = "";
            that.init(ele);
            that.animate();
        }
    }

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

        const modelFile = './public/module/mmd/miku_v2.pmd';
        const vmdFiles = ['./public/module/mmd/wavefile_v2.vmd'];

        let helper = (this.helper = new MMDAnimationHelper({
            afterglow: 2.0,
        }));

        const loader = new MMDLoader();
        this.resources.push(loader);
        loader.loadWithAnimation(
            modelFile,
            vmdFiles,
            function(mmd) {
                let mesh = mmd.mesh;
                mesh.position.y = -10;
                scene.add(mesh);

                that.helper.add(mesh, {
                    animation: mmd.animation,
                    physics: true,
                });

                let ikHelper = that.ikHelper = that.helper.objects.get(mesh).ikSolver.createHelper();
                ikHelper.visible = false;
                scene.add(ikHelper);

                let physicsHelper = that.physicsHelper = that.helper.objects.get(mesh).physics.createHelper();
                physicsHelper.visible = false;
                scene.add(physicsHelper);
                that.resources.push(physicsHelper);
                that.initGui();
            },
            onProgress,
            null
        );

        const controls = new OrbitControls(camera, renderer.domElement);
        controls.minDistance = 10;
        controls.maxDistance = 100;

        register.onResize(camera, renderer);
    }
    initGui() {
        let that = this;
        const api = {
            'animation': true,
            'ik': true,
            'outline': true,
            'physics': true,
            'show IK bones': false,
            'show rigid bodies': false
        };
        let guiDiv = document.querySelector('body .dg');
        if (guiDiv) {
            guiDiv.style.display = 'block'
        }
        const gui = that.gui = new GUI();

        gui.add(api, 'animation').onChange(function() {

            that.helper.enable('animation', api['animation']);

        });

        gui.add(api, 'ik').onChange(function() {

            that.helper.enable('ik', api['ik']);

        });

        gui.add(api, 'outline').onChange(function() {

            that.effect.enabled = api['outline'];

        });

        gui.add(api, 'physics').onChange(function() {

            that.helper.enable('physics', api['physics']);

        });

        gui.add(api, 'show IK bones').onChange(function() {

            that.ikHelper.visible = api['show IK bones'];

        });

        gui.add(api, 'show rigid bodies').onChange(function() {

            if (that.physicsHelper !== undefined) {
                that.physicsHelper.visible = api['show rigid bodies'];
            }

        });

    }

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
    }
    onWindowResize() {
        this.camera.aspect = window.innerWidth / window.innerHeight;
        this.camera.updateProjectionMatrix();

        this.renderer.setSize(window.innerWidth, window.innerHeight);
    }
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
            let guiDiv = document.querySelector('body .dg');
            if (guiDiv) {
                guiDiv.style.display = 'none'
            }
            for (let i = 0; i < this.resources.length; i++) {
                this.resources[i].dispose && this.resources[i].dispose();
                this.resources[i].clear && this.resources[i].clear();
            }
            console.debug(this.renderer.info);
        } catch (e) {
            console.error(e);
        }
    }
}
export { demo5 }