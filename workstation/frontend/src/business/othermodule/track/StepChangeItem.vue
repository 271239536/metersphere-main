<template>
  <el-form-item :label="formData.stepModel === 'STEP' ? $t('test_track.case.step_describe') : $t('test_track.case.text_describe')" :label-width="labelWidth">
    <el-dropdown placement="bottom-start" @command="handleCommand" v-if="!disable">
                  <span class="el-dropdown-link">
                    {{ $t('test_track.case.change_type') }} <i class="el-icon-arrow-down el-icon--right"/>
                  </span>
      <el-dropdown-menu>
        <el-dropdown-item :disabled="disable" command="STEP">
          <div>{{ $t('test_track.case.step_describe') }}</div>
<!--          <div>{{ $t('test_track.case.step_describe_tip') }}</div>-->
        </el-dropdown-item>
        <el-dropdown-item :disabled="disable" command="TEXT">
          <div>{{  $t('test_track.case.text_describe') }}</div>
<!--          <div>{{ $t('test_track.case.text_describe_tip') }}</div>-->
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </el-form-item>
</template>

<script>
export default {
  name: "StepChangeItem",
  props: ['form','labelWidth', 'disable'],
  computed:{
    formData() {
      return this.form;
    }
  },
  created() {
    if (!this.formData.stepModel) {
      this.formData.stepModel = "STEP";
    }
  },
  methods: {
    handleCommand(value) {
      if (this.disable) {
        return;
      }
      this.formData.stepModel = value;
    }
  }
}
</script>

<style scoped>
.el-dropdown-link {
  cursor: pointer;
  color: var(--primary_color);
}
.el-icon-arrow-down {
  font-size: 12px;
}

.step-change-item:hover {
  color: var(--primary_color);
  background-color: #F5F7FA;
}
</style>
