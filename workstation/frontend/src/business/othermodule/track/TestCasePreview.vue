<template>
  <el-dialog :close-on-click-modal="false" :visible.sync="visible" width="66%" top="5vh"
             @close="close" class="case-desc">
    <template v-slot:title>
      <ms-form-divider :title="$t('test_track.case.step_info')"/>
    </template>
    <div v-loading="loading" style="height: 70vh; overflow: auto">
      <el-form :model="formData">

        <form-rich-text-item :disabled="true" :label-width="formLabelWidth"
                             :title="$t('test_track.case.prerequisite')" :data="formData" prop="prerequisite"/>
        <step-change-item :label-width="formLabelWidth" :form="formData"/>
        <form-rich-text-item :disabled="true" :label-width="formLabelWidth" v-if="formData.stepModel === 'TEXT'"
                             :title="$t('test_track.case.step_desc')" :data="formData" prop="stepDescription"/>
        <form-rich-text-item :disabled="true" :label-width="formLabelWidth" v-if="formData.stepModel === 'TEXT'"
                             :title="$t('test_track.case.expected_results')" :data="formData" prop="expectedResult"/>
        <test-case-step-item :label-width="formLabelWidth" v-if="formData.stepModel === 'STEP' || !formData.stepModel"
                             :form="formData" :read-only="true"/>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
import MsFormDivider from "metersphere-frontend/src/components/MsFormDivider";
import FormRichTextItem from "metersphere-frontend/src/components/FormRichTextItem";
import StepChangeItem from "./StepChangeItem";
import TestCaseStepItem from "./TestCaseStepItem";

export default {
  name: "TestCasePreview",
  components: {
    MsFormDivider,
    FormRichTextItem,
    StepChangeItem,
    TestCaseStepItem,
  },
  props: ['loading'],
  data() {
    return {
      result: {},
      formLabelWidth: "100px",
      visible: false,
      formData: {}
    }
  },
  methods: {
    open() {
      this.visible = true;
    },
    close() {
      this.visible = false;
    },
    setData(data) {
      this.formData = data;
    }
  }
}
</script>

<style scoped>

</style>
