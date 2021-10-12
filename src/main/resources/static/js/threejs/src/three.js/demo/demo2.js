import * as THREE from 'three/build/three.module'
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader.js';
import { DRACOLoader } from 'three/examples/jsm/loaders/DRACOLoader.js';
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls.js';
import Stats from 'three/examples/jsm/libs/stats.module.js';
import { RoomEnvironment } from 'three/examples/jsm/environments/RoomEnvironment.js';
import register from '../register/register.js';
import { demo } from './demo';

class demo2 extends demo {
    constructor() {
        super();
        this.renderer;
        this.scene;
        this.loader;
        this.dracoLoader;
        this.animateId;
        this.resources = [];
    }

    render(ele) {
        const renderer = this.renderer = new THREE.WebGLRenderer({ antialias: true });
        renderer.setPixelRatio(window.devicePixelRatio);
        renderer.setSize(window.innerWidth, window.innerHeight);
        renderer.outputEncoding = THREE.sRGBEncoding;

        ele.appendChild(renderer.domElement);

        const scene = this.scene = new THREE.Scene();

        const pmremGenerator = new THREE.PMREMGenerator(renderer);
        scene.environment = pmremGenerator.fromScene(new RoomEnvironment(), 0.04).texture;
        this.resources.push(pmremGenerator);

        const camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight, 1, 500);
        camera.position.set(5, 2, 8);
        camera.lookAt(0, 0, 0);

        renderer.render(scene, camera);

        const loader = this.loader = new GLTFLoader();
        const dracoLoader = this.dracoLoader = new DRACOLoader();
        dracoLoader.setDecoderPath('./public/draco/');

        loader.setDRACOLoader(dracoLoader);
        let that = this;
        loader.load('./public/module/LittlestTokyo.glb', function(gltf) {

            const model = gltf.scene;
            model.position.set(1, 1, 0);
            model.scale.set(0.01, 0.01, 0.01);
            scene.add(model);
            that.resources.push(model);

            let mixer = new THREE.AnimationMixer(model);
            mixer.clipAction(gltf.animations[0]).play();

            const clock = new THREE.Clock();

            const stats = new Stats();
            ele.appendChild(stats.dom);

            const controls = new OrbitControls(camera, renderer.domElement);
            controls.target.set(0, 0.5, 0);
            controls.update();
            controls.enablePan = false;
            controls.enableDamping = true;
            that.resources.push(controls);

            animate();

            function animate() {

                that.animateId = requestAnimationFrame(animate);

                const delta = clock.getDelta();

                mixer.update(delta);

                controls.update();

                stats.update();

                renderer.render(scene, camera);

            }

        }, undefined, function(error) {
            console.error(error);
        });
        register.onResize(camera, renderer);
    }

    dispose() {
        try {
            this.scene.clear();
            this.renderer.dispose();
            this.renderer.forceContextLoss();
            this.renderer.content = null;
            cancelAnimationFrame(this.animateId)
            let gl = this.renderer.domElement.getContext("webgl");
            gl && gl.getExtension("WEBGL_lose_context").loseContext();
            this.dracoLoader && this.dracoLoader.dispose();
            for (let i = 0; i < this.resources.length; i++) {
                this.resources[i].dispose && this.resources[i].dispose();
                this.resources[i].clear && this.resources[i].clear();
            }
            console.log(this.renderer.info)
        } catch (e) {
            console.error(e)
        }
    }
}
export { demo2 }