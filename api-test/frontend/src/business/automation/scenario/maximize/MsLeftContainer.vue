<template>
  <el-aside class="ms-aside-container">
    <slot></slot>
    <ms-horizontal-drag-bar />
  </el-aside>
</template>

<script>
import MsHorizontalDragBar from 'metersphere-frontend/src/components/dragbar/MsLeft2RightDragBar';

export default {
  name: 'MsLeftContainer',
  components: { MsHorizontalDragBar },
  props: {
    width: {
      type: String,
      default: '500px',
    },
    enableAsideHidden: {
      type: Boolean,
      default: true,
    },
  },
  data() {
    return {
      asideHidden: false,
    };
  },
};
</script>

<style scoped>
.ms-aside-container {
  border: 1px solid #e6e6e6;
  padding: 10px;
  min-width: 690px;
  border-radius: 2px;
  box-sizing: border-box;
  background-color: #fff;
  height: calc(100vh - 60px);
  border-right: 0px;
  position: relative;
}

.scenario-aside {
  position: relative;
  border-radius: 4px;
  border: 1px solid #ebeef5;
  box-sizing: border-box;
}

.hiddenBottom i {
  margin-left: -2px;
}

.hiddenBottom:hover i {
  margin-left: 0;
  color: white;
}
</style>
