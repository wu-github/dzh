import * as THREE from 'three';
import { VRButton } from 'three/examples/jsm/webxr/VRButton.js';
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls.js';
import { DragControls } from 'three/examples/jsm/controls/DragControls.js'
import { XRControllerModelFactory } from 'three/examples/jsm/webxr/XRControllerModelFactory.js';

class demo4 {

    constructor() {
        this.renderer;
        this.controls;
        this.camera;
        this.scene;
        this.dragControls;
        this.controller1;
        this.controller2;
        this.controllerGrip1;
        this.controllerGrip2;
        this.raycaster = new THREE.Raycaster();
        this.mouse = new THREE.Vector2();
        this.enableSelection = false;
        this.intersected = [];
        this.tempMatrix = new THREE.Matrix4();
        this.group = new THREE.Group();
        this.selectedGroup = new THREE.Group();;
        this.objects = [];
    }

    render() {
        const ele = document.getElementById('three-view');
        this.renderer = new THREE.WebGLRenderer({ antialias: true });
        this.renderer.setPixelRatio(window.devicePixelRatio);
        this.renderer.setSize(window.innerWidth, window.innerHeight);
        ele.appendChild(VRButton.createButton(this.renderer));

        this.renderer.outputEncoding = THREE.sRGBEncoding;
        this.renderer.shadowMap.enabled = true;
        this.renderer.xr.enabled = true
        ele.appendChild(this.renderer.domElement);

        this.scene = new THREE.Scene();
        this.camera = new THREE.PerspectiveCamera(50, window.innerWidth / window.innerHeight, 0.1, 10);
        this.camera.position.set(0, 3, 1);
        this.camera.lookAt(0, 0, 0);

        this.scene.background = new THREE.Color(0x808080);

        this.controls = new OrbitControls(this.camera, ele);
        this.controls.target.set(0, 1.6, 0);
        this.controls.update();

        const floorGeometry = new THREE.PlaneGeometry(4, 4);
        const floorMaterial = new THREE.MeshStandardMaterial({
            color: 0xeeeeee,
            roughness: 1.0,
            metalness: 0.0
        });
        const floor = new THREE.Mesh(floorGeometry, floorMaterial);
        floor.rotation.x = -Math.PI / 2;
        floor.receiveShadow = true;
        this.scene.add(floor);

        this.scene.add(new THREE.HemisphereLight(0x808080, 0x606060));

        const light = new THREE.DirectionalLight(0xffffff);
        light.position.set(0, 6, 0);
        light.castShadow = true;
        light.shadow.camera.top = 2;
        light.shadow.camera.bottom = -2;
        light.shadow.camera.right = 2;
        light.shadow.camera.left = -2;
        light.shadow.mapSize.set(4096, 4096);
        this.scene.add(light);

        this.scene.add(this.group);
        this.scene.add(this.selectedGroup);

        const geometries = [
            new THREE.BoxGeometry(0.2, 0.2, 0.2),
            new THREE.ConeGeometry(0.2, 0.2, 64),
            new THREE.CylinderGeometry(0.2, 0.2, 0.2, 64),
            new THREE.IcosahedronGeometry(0.2, 8),
            new THREE.TorusGeometry(0.2, 0.04, 64, 32)
        ];

        for (let i = 0; i < 50; i++) {

            const geometry = geometries[Math.floor(Math.random() * geometries.length)];
            const material = new THREE.MeshStandardMaterial({
                color: Math.random() * 0xffffff,
                roughness: 0.7,
                metalness: 0.0
            });

            const object = new THREE.Mesh(geometry, material);

            object.position.x = Math.random() * 4 - 2;
            object.position.y = Math.random() * 2;
            object.position.z = Math.random() * 4 - 2;

            object.rotation.x = Math.random() * 2 * Math.PI;
            object.rotation.y = Math.random() * 2 * Math.PI;
            object.rotation.z = Math.random() * 2 * Math.PI;

            object.scale.setScalar(Math.random() + 0.5);

            object.castShadow = true;
            object.receiveShadow = true;

            this.group.add(object);
            this.objects.push(object);

        }

        // controllers

        this.controller1 = this.renderer.xr.getController(0);
        this.controller1.addEventListener('selectstart', this.onSelectStart);
        this.controller1.addEventListener('selectend', this.onSelectEnd);
        this.scene.add(this.controller1);

        this.controller2 = this.renderer.xr.getController(1);
        this.controller2.addEventListener('selectstart', this.onSelectStart);
        this.controller2.addEventListener('selectend', this.onSelectEnd);
        this.scene.add(this.controller2);

        const controllerModelFactory = new XRControllerModelFactory();

        this.controllerGrip1 = this.renderer.xr.getControllerGrip(0);
        this.controllerGrip1.add(controllerModelFactory.createControllerModel(this.controllerGrip1));
        this.scene.add(this.controllerGrip1);

        this.controllerGrip2 = this.renderer.xr.getControllerGrip(1);
        this.controllerGrip2.add(controllerModelFactory.createControllerModel(this.controllerGrip2));
        this.scene.add(this.controllerGrip2);

        const geometry = new THREE.BufferGeometry().setFromPoints([new THREE.Vector3(0, 0, 0), new THREE.Vector3(0, 0, -1)]);

        const line = new THREE.Line(geometry);
        line.name = 'line';
        line.scale.z = 5;

        this.controller1.add(line.clone());
        this.controller2.add(line.clone());

        this.animate();
        this.onDrag();

        document.addEventListener('click', this.onClick.bind(this));
        // document.addEventListener('mousedown', this.onMouseDown.bind(this));
        // document.addEventListener('mouseup', this.onMouseUp.bind(this));
        window.addEventListener('keydown', this.onKeyDown.bind(this));
        window.addEventListener('keyup', this.onKeyUp.bind(this));
    }

    onDrag() {
        let that = this;
        let objects = this.objects;
        this.dragControls = new DragControls([...objects], this.camera, this.renderer.domElement);

        this.dragControls.addEventListener('drag', function() {
            that.renderer.render(that.scene, that.camera);
        });

        // add event listener to highlight dragged objects
        this.dragControls.addEventListener('dragstart', function(event) {

        });

        this.dragControls.addEventListener('dragend', function(event) {

        });
    }

    onKeyDown(event) {
        if (event.keyCode === 16) {
            this.enableSelection = !this.controls.enabled;
        } else if (event.keyCode === 17) {
            this.controls.enabled = false;
        }
    }

    onKeyUp(event) {
        if (event.keyCode === 16 && this.enableSelection) {
            this.enableSelection = false;
            let length = this.selectedGroup.children.length;
            if (length > 0) {
                for (let i = length - 1; i >= 0; i--) {
                    let object = this.selectedGroup.children[i]
                    object.material.emissive.set(object.material._emissive);
                    this.group.attach(object);
                }
            }
            const draggableObjects = this.dragControls.getObjects();
            draggableObjects.length = 0;
            this.dragControls.transformGroup = false;
            draggableObjects.push(...this.objects);
        } else if (event.keyCode === 17) {
            this.controls.enabled = true;
        }

    }

    onMouseDown(event) {
        event.preventDefault();
        if (this.enableSelection === true) {
            let objects = this.objects;
            const draggableObjects = this.dragControls.getObjects();
            draggableObjects.length = 0;

            this.mouse.x = (event.clientX / window.innerWidth) * 2 - 1;
            this.mouse.y = -(event.clientY / window.innerHeight) * 2 + 1;

            this.raycaster.setFromCamera(this.mouse, this.camera);
            const intersections = this.raycaster.intersectObjects(objects, true);

            if (intersections.length > 0) {

                const object = intersections[0].object;

                if (this.selectedGroup.children.includes(object) === true) {
                    // object.material.emissive.set(object.material._emissive);
                    // this.group.attach(object);

                } else {
                    object.material._emissive = object.material.emissive.clone();
                    object.material.emissive.set(0x0000ff);
                    this.selectedGroup.attach(object);
                }

                this.dragControls.transformGroup = true;
                draggableObjects.push(this.selectedGroup);

            }

            if (this.selectedGroup.children.length === 0) {

                this.dragControls.transformGroup = false;
                draggableObjects.push(...objects);

            }

        }

        this.renderer.render(this.scene, this.camera);
    }

    onMouseUp(event) {
        event.preventDefault();
        if (this.enableSelection === true) {
            let objects = this.objects;
            const draggableObjects = this.dragControls.getObjects();
            draggableObjects.length = 0;

            this.mouse.x = (event.clientX / window.innerWidth) * 2 - 1;
            this.mouse.y = -(event.clientY / window.innerHeight) * 2 + 1;

            this.raycaster.setFromCamera(this.mouse, this.camera);
            const intersections = this.raycaster.intersectObjects(objects, true);

            if (intersections.length > 0) {

                const object = intersections[0].object;

                if (this.selectedGroup.children.includes(object) === true) {
                    object.material.emissive.set(object.material._emissive);
                    this.group.attach(object);

                } else {
                    // object.material._emissive = object.material.emissive.clone();
                    // object.material.emissive.set(0x0000ff);
                    // this.selectedGroup.attach(object);
                }

                this.dragControls.transformGroup = true;
                draggableObjects.push(this.selectedGroup);

            }

            if (this.selectedGroup.children.length === 0) {

                this.dragControls.transformGroup = false;
                draggableObjects.push(...objects);

            }

        }

        this.renderer.render(this.scene, this.camera);
    }

    onClick(event) {
        event.preventDefault();

        if (this.enableSelection === true) {
            let objects = this.objects;
            const draggableObjects = this.dragControls.getObjects();
            draggableObjects.length = 0;

            this.mouse.x = (event.clientX / window.innerWidth) * 2 - 1;
            this.mouse.y = -(event.clientY / window.innerHeight) * 2 + 1;

            this.raycaster.setFromCamera(this.mouse, this.camera);
            const intersections = this.raycaster.intersectObjects(objects, true);

            if (intersections.length > 0) {

                const object = intersections[0].object;

                if (this.selectedGroup.children.includes(object) === true) {
                    object.material.emissive.set(object.material._emissive);
                    this.group.attach(object);

                } else {
                    object.material._emissive = object.material.emissive.clone();
                    object.material.emissive.set(0x0000ff);
                    this.selectedGroup.attach(object);
                }

                this.dragControls.transformGroup = true;
                draggableObjects.push(this.selectedGroup);

            }

            if (this.selectedGroup.children.length === 0) {

                this.dragControls.transformGroup = false;
                draggableObjects.push(...objects);

            }

        }

        this.renderer.render(this.scene, this.camera);

    }

    onSelectStart(event) {

        const controller = event.target;

        const intersections = this.getIntersections(controller);

        if (intersections.length > 0) {

            const intersection = intersections[0];

            const object = intersection.object;
            object.material.emissive.b = 1;
            controller.attach(object);

            controller.userData.selected = object;

        }

    }

    onSelectEnd(event) {

        const controller = event.target;

        if (controller.userData.selected !== undefined) {

            const object = controller.userData.selected;
            object.material.emissive.b = 0;
            this.group.attach(object);

            controller.userData.selected = undefined;

        }


    }

    getIntersections(controller) {

        this.tempMatrix.identity().extractRotation(controller.matrixWorld);

        this.raycaster.ray.origin.setFromMatrixPosition(controller.matrixWorld);
        this.raycaster.ray.direction.set(0, 0, -1).applyMatrix4(this.tempMatrix);

        return this.raycaster.intersectObjects(this.group.children, false);

    }

    intersectObjects(controller) {
        // Do not highlight when already selected

        if (controller.userData.selected !== undefined) {
            return;
        }

        const line = controller.getObjectByName('line');
        const intersections = this.getIntersections(controller);

        if (intersections.length > 0) {
            const intersection = intersections[0];

            const object = intersection.object;
            object.material.emissive.r = 1;
            this.intersected.push(object);
            line.scale.z = intersection.distance;

        } else {
            line.scale.z = 5;
        }

    }

    cleanIntersected() {
        while (this.intersected.length) {
            const object = this.intersected.pop();
            object.material.emissive.r = 0;
        }

    }

    animate() {
        let that = this;
        that.renderer.setAnimationLoop(function() {
            that.cleanIntersected();

            that.intersectObjects(that.controller1);
            that.intersectObjects(that.controller2);

            that.renderer.render(that.scene, that.camera);
        });

    }

}

export { demo4 }