import * as THREE from 'three/build/three.module'
import { OBJLoader } from 'three/examples/jsm/loaders/OBJLoader.js';
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls.js';
import register from '../register/register.js';
import { demo } from './demo';

class demo6 extends demo {
    constructor() {
        super();
        this.renderer;
        this.camera;
        this.scene;
        this.loader;
        this.object;
        this.animateId;
        this.resources = [];
        this.mouseX = 0;
        this.mouseY = 0;
    }

    render(ele) {
        let that = this;
        const renderer = this.renderer = new THREE.WebGLRenderer({ antialias: true });
        renderer.setPixelRatio(window.devicePixelRatio);
        renderer.setSize(window.innerWidth, window.innerHeight);

        ele.appendChild(renderer.domElement);

        const scene = this.scene = new THREE.Scene();

        const camera = this.camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight, 1, 2000);
        camera.position.set(0, 0, 250);
        camera.lookAt(0, 0, 0);

        const ambientLight = new THREE.AmbientLight(0xcccccc, 0.4);
        scene.add(ambientLight);
        this.resources.push(ambientLight);

        const pointLight = new THREE.PointLight(0xffffff, 0.8);
        camera.add(pointLight);
        this.resources.push(pointLight);
        scene.add(camera);

        const controls = new OrbitControls(camera, renderer.domElement);
        controls.target.set(0, 0.5, 0);
        controls.update();
        controls.enablePan = false;
        controls.enableDamping = true;
        that.resources.push(controls);

        const textureLoader = new THREE.TextureLoader(manager);
        const texture = textureLoader.load('./public/module/3dmax/uv_grid_opengl.jpg');

        function loadModel() {
            that.object.traverse(function(child) {
                // if (child.isMesh) child.material.map = texture;
            });
            that.object.position.y = -95;
            scene.add(that.object);
        }
        const manager = new THREE.LoadingManager(loadModel);
        manager.onProgress = function(item, loaded, total) {
            console.log(item, loaded, total);
        };

        function onProgress(xhr) {
            if (xhr.lengthComputable) {
                const percentComplete = xhr.loaded / xhr.total * 100;
                console.log('model ' + Math.round(percentComplete, 2) + '% downloaded');
            }
        }

        function onError() {}

        const loader = this.loader = new OBJLoader(manager);
        loader.load('./public/module/3dmax/male02.obj', function(obj) {
            that.object = obj;
            that.resources.push(that.object);
        }, onProgress, onError);

        renderer.render(scene, camera);

        animate();

        function animate() {
            that.animateId = requestAnimationFrame(animate);
            controls.update();

            function a() {
                that.camera.position.x += (that.mouseX - that.camera.position.x) * .05;
                that.camera.position.y += (-that.mouseY - that.camera.position.y) * .05;
                that.camera.lookAt(that.scene.position);
                that.renderer.render(that.scene, that.camera);
            }
            a();
        }

        register.onResize(camera, renderer);
        document.addEventListener('mousemove', this.onDocumentMouseMove.bind(this));
    }

    onDocumentMouseMove(event) {
        this.mouseX = (event.clientX - window.innerWidth / 2) / 2;
        this.mouseY = (event.clientY - window.innerHeight / 2) / 2;
    }

    dispose() {
        try {
            this.scene.clear();
            this.camera.clear();
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
            console.debug(this.renderer.info);
        } catch (e) {
            console.error(e)
        }
    }
}
export { demo6 }