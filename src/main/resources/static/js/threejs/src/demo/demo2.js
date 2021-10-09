import * as THREE from 'three/build/three.module'
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader.js';
import { DRACOLoader } from 'three/examples/jsm/loaders/DRACOLoader.js';
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls.js';
import Stats from 'three/examples/jsm/libs/stats.module.js';
import { RoomEnvironment } from 'three/examples/jsm/environments/RoomEnvironment.js';
import register from '../register/register';
class demo2 {
    render() {
        const ele = document.getElementById('three-view');
        const renderer = new THREE.WebGLRenderer({ antialias: true });
        renderer.setPixelRatio(window.devicePixelRatio);
        renderer.setSize(window.innerWidth, window.innerHeight);
        renderer.outputEncoding = THREE.sRGBEncoding;

        ele.appendChild(renderer.domElement);

        const scene = new THREE.Scene();

        const pmremGenerator = new THREE.PMREMGenerator(renderer);
        scene.environment = pmremGenerator.fromScene(new RoomEnvironment(), 0.04).texture;

        const camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight, 1, 500);
        camera.position.set(5, 2, 8);
        camera.lookAt(0, 0, 0);

        renderer.render(scene, camera);

        const loader = new GLTFLoader();
        const dracoLoader = new DRACOLoader();
        dracoLoader.setDecoderPath('./public/draco/');

        loader.setDRACOLoader(dracoLoader);
        loader.load('./public/module/LittlestTokyo.glb', function(gltf) {

            const model = gltf.scene;
            model.position.set(1, 1, 0);
            model.scale.set(0.01, 0.01, 0.01);
            scene.add(model);

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

            animate();

            function animate() {

                requestAnimationFrame(animate);

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
}
export { demo2 }