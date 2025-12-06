<template>
  <div 
    class="custom-cursor" 
    :class="{ 'hovering': isHovering }"
    :style="{ transform: `translate(${x}px, ${y}px)` }"
  >
    <div class="cursor-dot"></div>
    <div class="cursor-outline"></div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';

const x = ref(0);
const y = ref(0);
const isHovering = ref(false);

const updateCursor = (e) => {
  x.value = e.clientX;
  y.value = e.clientY;
};

// 检测鼠标是否悬停在可交互元素上
const handleMouseOver = (e) => {
  if (e.target.closest('a, button, .clickable, input, textarea, .card-hover')) {
    isHovering.value = true;
  } else {
    isHovering.value = false;
  }
};

onMounted(() => {
  document.addEventListener('mousemove', updateCursor);
  document.addEventListener('mouseover', handleMouseOver);
  // 隐藏默认光标
  document.body.style.cursor = 'none';
});

onUnmounted(() => {
  document.removeEventListener('mousemove', updateCursor);
  document.removeEventListener('mouseover', handleMouseOver);
  document.body.style.cursor = 'auto';
});
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variables.scss';

.custom-cursor {
  position: fixed;
  top: 0;
  left: 0;
  width: 32px;
  height: 32px;
  pointer-events: none;
  z-index: 9999;
  mix-blend-mode: difference; // 让光标在不同背景下可见
  margin-left: -16px; // Center
  margin-top: -16px; // Center
  transition: width 0.3s, height 0.3s, background-color 0.3s;

  .cursor-dot {
    position: absolute;
    top: 50%;
    left: 50%;
    width: 8px;
    height: 8px;
    background-color: white;
    border-radius: 50%;
    transform: translate(-50%, -50%);
  }

  .cursor-outline {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    border: 1px solid rgba(255, 255, 255, 0.5);
    border-radius: 50%;
    transition: all 0.15s ease-out;
  }

  &.hovering {
    .cursor-outline {
      transform: scale(1.5);
      background-color: rgba(255, 255, 255, 0.1);
      border-color: transparent;
    }
    .cursor-dot {
      opacity: 0; // Hide dot when expanding
    }
  }
}

@media (max-width: $breakpoint-tablet) {
  .custom-cursor {
    display: none; // 移动端隐藏自定义光标
  }
}
</style>

