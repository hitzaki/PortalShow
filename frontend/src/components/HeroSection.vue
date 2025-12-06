<template>
  <section id="home" class="hero-section">
    <canvas ref="canvasRef" class="particles-canvas"></canvas>
    
    <div class="hero-content container">
      <h1 class="hero-title">
        为企业家打造
        <span class="highlight gradient-text">{{ typeWriterText }}</span>
        <span class="cursor" :class="{ blink: isBlinking }">|</span>
      </h1>
      
      <p class="hero-subtitle" ref="subtitleRef">
        融合美学与技术的数字化解决方案，提升品牌价值
      </p>
      
      <div class="hero-actions" ref="actionsRef">
        <button class="btn-primary btn-lg" @click="scrollToServices">
          探索服务
          <span class="arrow">→</span>
        </button>
        <button class="btn-outline btn-lg">观看演示</button>
      </div>
    </div>

    <div class="scroll-indicator">
      <div class="mouse">
        <div class="wheel"></div>
      </div>
      <span>向下滚动</span>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import gsap from 'gsap';
import ScrollToPlugin from 'gsap/ScrollToPlugin';

gsap.registerPlugin(ScrollToPlugin);

// Typewriter Effect
const fullText = "数字名片";
const typeWriterText = ref("");
const isBlinking = ref(true);
let typeInterval;

// Canvas Particles
const canvasRef = ref(null);
let ctx;
let particles = [];
let animationFrameId;
let mouse = { x: null, y: null };

const resizeCanvas = () => {
  if (canvasRef.value) {
    canvasRef.value.width = window.innerWidth;
    canvasRef.value.height = window.innerHeight;
  }
};

class Particle {
  constructor() {
    this.x = Math.random() * canvasRef.value.width;
    this.y = Math.random() * canvasRef.value.height;
    this.size = Math.random() * 2 + 1;
    this.speedX = Math.random() * 1 - 0.5;
    this.speedY = Math.random() * 1 - 0.5;
    this.color = `rgba(37, 99, 235, ${Math.random() * 0.5})`; // Tech Blue opacity
  }

  update() {
    this.x += this.speedX;
    this.y += this.speedY;

    // Mouse interaction
    if (mouse.x != null) {
      const dx = mouse.x - this.x;
      const dy = mouse.y - this.y;
      const distance = Math.sqrt(dx * dx + dy * dy);
      if (distance < 100) {
        const forceDirectionX = dx / distance;
        const forceDirectionY = dy / distance;
        const force = (100 - distance) / 100;
        this.speedX -= forceDirectionX * force * 0.5;
        this.speedY -= forceDirectionY * force * 0.5;
      }
    }

    if (this.x > canvasRef.value.width || this.x < 0) this.speedX *= -1;
    if (this.y > canvasRef.value.height || this.y < 0) this.speedY *= -1;
  }

  draw() {
    ctx.fillStyle = this.color;
    ctx.beginPath();
    ctx.arc(this.x, this.y, this.size, 0, Math.PI * 2);
    ctx.fill();
  }
}

const initParticles = () => {
  particles = [];
  const particleCount = window.innerWidth < 768 ? 50 : 100;
  for (let i = 0; i < particleCount; i++) {
    particles.push(new Particle());
  }
};

const animateParticles = () => {
  if (!canvasRef.value) return;
  ctx.clearRect(0, 0, canvasRef.value.width, canvasRef.value.height);
  
  particles.forEach(particle => {
    particle.update();
    particle.draw();
  });

  // Connect particles
  for (let a = 0; a < particles.length; a++) {
    for (let b = a; b < particles.length; b++) {
      const dx = particles[a].x - particles[b].x;
      const dy = particles[a].y - particles[b].y;
      const distance = Math.sqrt(dx * dx + dy * dy);

      if (distance < 100) {
        ctx.strokeStyle = `rgba(139, 92, 246, ${1 - distance/100})`; // Purple connection
        ctx.lineWidth = 0.5;
        ctx.beginPath();
        ctx.moveTo(particles[a].x, particles[a].y);
        ctx.lineTo(particles[b].x, particles[b].y);
        ctx.stroke();
      }
    }
  }
  animationFrameId = requestAnimationFrame(animateParticles);
};

const handleMouseMove = (e) => {
  mouse.x = e.x;
  mouse.y = e.y;
};

const scrollToServices = () => {
  gsap.to(window, { duration: 1, scrollTo: { y: "#services", offsetY: 80 }, ease: "power3.inOut" });
};

// Animations
const subtitleRef = ref(null);
const actionsRef = ref(null);

onMounted(() => {
  // Canvas Setup
  if (canvasRef.value) {
    ctx = canvasRef.value.getContext('2d');
    resizeCanvas();
    initParticles();
    animateParticles();
    window.addEventListener('resize', () => {
      resizeCanvas();
      initParticles();
    });
    window.addEventListener('mousemove', handleMouseMove);
  }

  // Typewriter Logic
  let i = 0;
  typeInterval = setInterval(() => {
    if (i < fullText.length) {
      typeWriterText.value += fullText.charAt(i);
      i++;
    } else {
      clearInterval(typeInterval);
      isBlinking.value = true;
    }
  }, 150);

  // GSAP Entrance
  const tl = gsap.timeline();
  tl.from('.hero-title', { y: 50, opacity: 0, duration: 1, ease: "power3.out", delay: 0.5 })
    .from(subtitleRef.value, { y: 30, opacity: 0, duration: 0.8 }, "-=0.5")
    .from(actionsRef.value, { y: 20, opacity: 0, duration: 0.8 }, "-=0.6")
    .from('.scroll-indicator', { opacity: 0, duration: 1 }, "-=0.4");
});

onUnmounted(() => {
  cancelAnimationFrame(animationFrameId);
  window.removeEventListener('resize', resizeCanvas);
  window.removeEventListener('mousemove', handleMouseMove);
  clearInterval(typeInterval);
});
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variables.scss';

.hero-section {
  position: relative;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  background: radial-gradient(circle at 50% 50%, #1e293b 0%, $color-bg-dark 100%);
}

.particles-canvas {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1;
}

.hero-content {
  position: relative;
  z-index: 2;
  text-align: center;
  max-width: 800px;
}

.hero-title {
  font-size: clamp(40px, 5vw, 80px);
  font-weight: 800;
  margin-bottom: 24px;
  line-height: 1.1;

  .gradient-text {
    @include text-gradient;
  }

  .cursor {
    display: inline-block;
    font-weight: 100;
    color: $color-primary;
    &.blink {
      animation: blink 1s step-end infinite;
    }
  }
}

.hero-subtitle {
  font-size: clamp(18px, 2vw, 24px);
  color: $color-text-muted;
  margin-bottom: 48px;
  max-width: 600px;
  margin-left: auto;
  margin-right: auto;
}

.hero-actions {
  display: flex;
  gap: 20px;
  justify-content: center;
  
  .btn-lg {
    padding: 16px 32px;
    font-size: 18px;
    border-radius: 50px;
    font-weight: 600;
    display: flex;
    align-items: center;
    gap: 10px;
    transition: all 0.3s;

    .arrow {
      transition: transform 0.3s;
    }

    &:hover .arrow {
      transform: translateX(5px);
    }
  }

  .btn-primary {
    background: linear-gradient(135deg, $color-primary, $color-secondary);
    color: white;
    box-shadow: 0 10px 30px -10px rgba($color-primary, 0.6);

    &:hover {
      transform: translateY(-3px);
      box-shadow: 0 20px 40px -10px rgba($color-primary, 0.8);
    }
  }

  .btn-outline {
    background: transparent;
    border: 2px solid rgba(255,255,255,0.2);
    color: white;

    &:hover {
      border-color: white;
      background: rgba(255,255,255,0.1);
    }
  }
}

.scroll-indicator {
  position: absolute;
  bottom: 40px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 2;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  color: $color-text-muted;
  font-size: 12px;
  opacity: 0.8;

  .mouse {
    width: 26px;
    height: 40px;
    border: 2px solid rgba(255,255,255,0.3);
    border-radius: 20px;
    position: relative;

    .wheel {
      width: 4px;
      height: 6px;
      background: white;
      border-radius: 2px;
      position: absolute;
      top: 6px;
      left: 50%;
      transform: translateX(-50%);
      animation: scroll 1.5s infinite;
    }
  }
}

@keyframes blink {
  0%, 100% { opacity: 1; }
  50% { opacity: 0; }
}

@keyframes scroll {
  0% { transform: translate(-50%, 0); opacity: 1; }
  100% { transform: translate(-50%, 15px); opacity: 0; }
}
</style>

