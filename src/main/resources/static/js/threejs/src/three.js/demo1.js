import * as THREE from 'three';
import helvetiker_regular from 'three/examples/fonts/helvetiker_regular.typeface.json'
import gentilis_regular from 'three/examples/fonts/gentilis_regular.typeface.json'
import constants from '../constants/constants.js'
import register from './register/register.js';

class demo1 {
    render() {
        const ele = document.getElementById('three-view');
        const renderer = new THREE.WebGLRenderer();
        renderer.setSize(window.innerWidth, window.innerHeight);
        ele.appendChild(renderer.domElement);

        const camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight, 1, 500);
        camera.position.set(0, 0, 100);
        camera.lookAt(0, 0, 0);

        const scene = new THREE.Scene();
        //line
        const material = new THREE.LineDashedMaterial({ color: 0xff0000 }); //new THREE.LineBasicMaterial({ color: 0x0000ff });
        const points = [];
        points.push(new THREE.Vector3(-10, 0, 0));
        points.push(new THREE.Vector3(0, 10, 0));
        points.push(new THREE.Vector3(10, 0, 0));

        const geometry = new THREE.BufferGeometry().setFromPoints(points);
        const line = new THREE.Line(geometry, material);
        scene.add(line);
        //text
        const testG = new THREE.TextGeometry('wurd', {
            font: new THREE.Font(gentilis_regular), //new THREE.Font(helvetiker_regular),
            size: 10,
            height: 5,
            curveSegments: 12,
            bevelEnabled: false,
            bevelThickness: 1,
            bevelSize: 1,
            bevelSegments: 3
        });
        // const textMaterial = new THREE.MeshNormalMaterial({ flatShading: THREE.FlatShading });
        const textMaterial = new THREE.MeshBasicMaterial({
            color: 0xff0000,
            flatShading: true
        })
        const textMesh = new THREE.Mesh(testG, textMaterial)
        textMesh.position.set(-15, 10, 10);
        scene.add(textMesh);

        renderer.render(scene, camera);

        register.onResize(camera, renderer);

    }
}
export { demo1 }