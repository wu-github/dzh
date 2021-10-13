import { demo1 } from '../demo/demo1.js';
import { demo2 } from '../demo/demo2.js';
import { demo3 } from '../demo/demo3.js';
import { demo4 } from '../demo/demo4.js';

class Register {
    constructor() {
        this.examples = [];
        this.regist();
    }
    regist() {
        this.examples.push({ title: 'demo1', view: demo1 });
        this.examples.push({ title: 'demo2', view: demo2 });
        this.examples.push({ title: 'demo3', view: demo3 });
        this.examples.push({ title: 'demo4', view: demo4 });
    }
    onResize(camera, renderer) {
        window.addEventListener('resize', onWindowResize);

        function onWindowResize() {

            camera.aspect = window.innerWidth / window.innerHeight;
            camera.updateProjectionMatrix();

            renderer.setSize(window.innerWidth, window.innerHeight);

        }
    }
}
const register = new Register();
export default register