var Rain = Rain || {};

document.body.onload = function(){
    Rain.sprites();
}

window.onresize = function(){

}

Rain.sprites = function() {
    var height = window.innerHeight;
    var width = window.innerWidth;
    var ele = document.getElementById('rainContainer');

    var Engine = Matter.Engine;
    var Render = Matter.Render;
    var Runner =  Matter.Runner;
    var Composites = Matter.Composites;
    var Common = Matter.Common;
    var MouseConstraint = Matter.MouseConstraint;
    var Mouse = Matter.Mouse;
    var Composite = Matter.Composite;
    var Bodies = Matter.Bodies;

    // create engine
    var engine = Engine.create(),
        world = engine.world;

    // create renderer
    var render = Render.create({
        element: ele,
        engine: engine,
        options: {
            width: width,
            height: height,
            showAngleIndicator: false,
            wireframes: false
        }
    });

    // add bodies
    var options = {
            isStatic: true
        };

    world.bodies = [];
    //rectangle(横向中心, 纵向中心, 宽度, 高度, options)
    Composite.add(world, [
        Bodies.rectangle(width/2, height, width, 5, options),
    ]);

    var scale = 0.1;
    var ballSize = 46 * scale;
    var stack = Composites.stack(0, 20, width/((ballSize-0.2)*2), 4, 0, 0, function(x, y) {
        return Bodies.circle(x, y, ballSize, {
                        density: 0.0005,
                        frictionAir: 0.06,
                        restitution: 0.3,
                        friction: 0.01,
                        render: {
                            sprite: {
                                texture: '../../img/rain/ball.png',
                                xScale: scale,
                                yScale: scale,
                            }
                        }
                    });
    });

    Composite.add(world, stack);

    // add mouse control
    var mouse = Mouse.create(render.canvas),
        mouseConstraint = MouseConstraint.create(engine, {
            mouse: mouse,
            constraint: {
                stiffness: 0.2,
                render: {
                    visible: false
                }
            }
        });

    Composite.add(world, mouseConstraint);

    // keep the mouse in sync with rendering
    render.mouse = mouse;

    // fit the render viewport to the scene
    Render.lookAt(render, {
        min: { x: 0, y: 0},
        max: { x: width, y: height }
    });

    Render.run(render);

    // create runner
    var runner = Runner.create();
    Runner.run(runner, engine);

};

