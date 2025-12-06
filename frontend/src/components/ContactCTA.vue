<template>
  <section id="contact" class="cta-section section-padding">
    <div class="bg-gradient"></div>
    
    <div class="container contact-container">
      <div class="contact-header">
        <h2 class="section-title">开启您的数字化旅程</h2>
        <p>填写下表，我们将为您提供免费的项目评估与咨询。</p>
      </div>

      <form class="contact-form" @submit.prevent="handleSubmit">
        <div class="form-group">
          <input type="text" id="name" required placeholder=" " v-model="form.name">
          <label for="name">您的姓名</label>
        </div>

        <div class="form-group">
          <input type="email" id="email" required placeholder=" " v-model="form.email">
          <label for="email">电子邮箱</label>
        </div>

        <div class="form-group">
          <input type="tel" id="phone" placeholder=" " v-model="form.phone">
          <label for="phone">联系电话</label>
        </div>

        <div class="form-group">
          <textarea id="message" required placeholder=" " v-model="form.message" rows="4"></textarea>
          <label for="message">项目需求描述</label>
        </div>

        <button type="submit" class="btn-submit" :class="{ 'submitting': isSubmitting }">
          <span v-if="!isSubmitting">发送咨询</span>
          <span v-else>发送中...</span>
          <div class="particles" v-if="!isSubmitting"></div>
        </button>
      </form>
    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';

const form = ref({ name: '', email: '', phone: '', message: '' });
const isSubmitting = ref(false);

const handleSubmit = async () => {
  isSubmitting.value = true;
  try {
    const res = await axios.post('http://localhost:8064/portalShow/contact/submit', form.value);
    if (res.data.code === 0) {
      alert("感谢您的咨询！我们会尽快联系您。");
      form.value = { name: '', email: '', phone: '', message: '' };
    } else {
      alert(res.data.msg || "提交失败，请稍后重试");
    }
  } catch (error) {
    console.error(error);
    alert("网络错误，请稍后重试");
  } finally {
    isSubmitting.value = false;
  }
};
</script>

<style lang="scss" scoped>
@import '@/assets/styles/variables.scss';

.cta-section {
  position: relative;
  overflow: hidden;
  min-height: 600px;
  display: flex;
  align-items: center;
}

.bg-gradient {
  position: absolute;
  top: 0; left: 0;
  width: 100%; height: 100%;
  background: linear-gradient(45deg, #0f172a, #1e1b4b, #312e81);
  background-size: 200% 200%;
  animation: gradientBG 10s ease infinite;
  z-index: -1;
}

.contact-container {
  max-width: 600px;
  background: rgba(255,255,255,0.05);
  backdrop-filter: blur(20px);
  padding: 40px;
  border-radius: 24px;
  border: 1px solid rgba(255,255,255,0.1);
}

.contact-header {
  text-align: center;
  margin-bottom: 40px;
  
  p { color: $color-text-muted; }
}

.contact-form {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.form-group {
  position: relative;

  input, textarea {
    width: 100%;
    padding: 16px;
    background: rgba(0,0,0,0.2);
    border: 1px solid rgba(255,255,255,0.1);
    border-radius: 8px;
    color: white;
    font-size: 16px;
    transition: all 0.3s;

    &:focus {
      border-color: $color-primary;
      background: rgba(0,0,0,0.3);
    }

    // Floating label logic
    &:focus + label,
    &:not(:placeholder-shown) + label {
      top: -10px;
      left: 10px;
      font-size: 12px;
      color: $color-primary;
      background: $color-bg-dark;
      padding: 0 5px;
    }
  }

  label {
    position: absolute;
    left: 16px;
    top: 16px;
    color: $color-text-muted;
    transition: all 0.3s;
    pointer-events: none;
  }
}

.btn-submit {
  padding: 16px;
  background: $color-primary;
  color: white;
  font-size: 16px;
  font-weight: 600;
  border-radius: 8px;
  position: relative;
  overflow: hidden;
  transition: transform 0.2s;

  &:hover {
    transform: scale(1.02);
    background: lighten($color-primary, 5%);
  }

  &.submitting {
    opacity: 0.7;
    cursor: wait;
  }
}

@keyframes gradientBG {
  0% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
  100% { background-position: 0% 50%; }
}
</style>
