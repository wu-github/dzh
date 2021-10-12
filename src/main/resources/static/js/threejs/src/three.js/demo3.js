import * as THREE from 'three';
import register from './register/register';

class demo3 {
    render() {
        const ele = document.getElementById('three-view');
        const renderer = new THREE.WebGLRenderer();
        renderer.setSize(window.innerWidth, window.innerHeight);
        ele.appendChild(renderer.domElement);
        const camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight, 1, 500);
        camera.position.set(0, 0, 100);
        camera.lookAt(0, 0, 0);

        const scene = new THREE.Scene();
        const MAX_POINTS = 500;

        // geometry
        const geometry = new THREE.BufferGeometry();

        // attributes
        const positions = new Float32Array(MAX_POINTS * 3); // 3 vertices per point

        geometry.setAttribute('position', new THREE.BufferAttribute(positions, 3));

        // draw range
        const drawCount = 2; // draw the first 2 points, only
        geometry.setDrawRange(0, drawCount);

        // material
        const material = new THREE.LineBasicMaterial({ color: 0xffff00 });

        // line
        const line = new THREE.Line(geometry, material);

        scene.add(line);

        renderer.render(scene, camera);

        line.geometry.attributes.position.needsUpdate = true;

        // material.needsUpdate = true
        const linePostsion = line.geometry.attributes.position.array;

        let x, y, z, index;
        x = y = z = index = 0;

        for (let i = 0, l = MAX_POINTS; i < l; i++) {

            linePostsion[index++] = x;
            linePostsion[index++] = y;
            linePostsion[index++] = z;

            x += (Math.random() - 0.5) * 30;
            y += (Math.random() - 0.5) * 30;
            z += (Math.random() - 0.5) * 30;

        }

        // renderer.render(scene, camera);
        renderer.render(line, camera);

        register.onResize(camera, renderer);

    }
}

export { demo3 }