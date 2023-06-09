<template>
  <api-base-component
    @copy="copyRow"
    @remove="remove"
    @active="active"
    :data="request"
    :draggable="draggable"
    :color="defColor"
    :is-max="isMax"
    :show-btn="showBtn"
    :show-version="showVersion"
    :background-color="defBackgroundColor"
    :title="request.elementType">
    <div style="height: 300px; width: 100%">
      <ms-code-edit mode="xml" :data.sync="request.jmeterElement" theme="eclipse" ref="codeEdit" />
    </div>

    <template v-slot:debugStepCode>
      <span v-if="node.data.testing" class="ms-test-running">
        <i class="el-icon-loading" style="font-size: 16px" />
        {{ $t('commons.testing') }}
      </span>
      <span
        class="ms-step-debug-code"
        :class="node.data.code === 'ERROR' ? 'ms-req-error' : 'ms-req-success'"
        v-if="!loading && !node.data.testing && node.data.debug">
        {{ getCode() }}
      </span>
    </template>
  </api-base-component>
</template>

<script>
import MsCodeEdit from 'metersphere-frontend/src/components/MsCodeEdit';
import MsInstructionsIcon from 'metersphere-frontend/src/components/MsInstructionsIcon';
import MsDropdown from '@/business/commons/MsDropdown';
import ApiBaseComponent from '../common/ApiBaseComponent';
import Jsr233ProcessorContent from '../common/Jsr233ProcessorContent';

export default {
  name: 'JmeterElementComponent',
  components: {
    Jsr233ProcessorContent,
    ApiBaseComponent,
    MsDropdown,
    MsInstructionsIcon,
    MsCodeEdit,
  },
  props: {
    draggable: {
      type: Boolean,
      default: false,
    },
    message: String,
    isReadOnly: {
      type: Boolean,
      default: false,
    },
    isMax: {
      type: Boolean,
      default: false,
    },
    showBtn: {
      type: Boolean,
      default: true,
    },
    showVersion: {
      type: Boolean,
      default: true,
    },
    request: {
      type: Object,
    },
    defTitle: { type: String, default: 'Jmeter组件' },
    defColor: { type: String, default: '#606260' },
    defBackgroundColor: { type: String, default: '#F4F4FF' },
    node: {},
  },
  data() {
    return {
      loading: false,
    };
  },
  watch: {
    message() {
      this.reload();
    },
  },
  methods: {
    reload() {
      this.loading = true;
      this.$nextTick(() => {
        this.loading = false;
      });
    },
    getCode() {
      if (this.node && this.node.data.code && this.node.data.debug) {
        let status = this.node.data.code;
        return status.toLowerCase()[0].toUpperCase() + status.toLowerCase().substr(1);
      }
      return '';
    },
    remove() {
      this.$emit('remove', this.request, this.node);
    },
    copyRow() {
      this.$emit('copyRow', this.request, this.node);
    },
    active() {
      this.request.active = !this.request.active;
      if (this.node) {
        this.node.expanded = this.request.active;
      }
    },
  },
};
</script>

<style scoped>
:deep(.el-divider) {
  margin-bottom: 10px;
}
.ms-req-error {
  color: #f56c6c;
}

.ms-req-success {
  color: #67c23a;
}
.ms-step-debug-code {
  display: inline-block;
  margin: 0 5px;
  overflow-x: hidden;
  padding-bottom: 0;
  text-overflow: ellipsis;
  vertical-align: middle;
  white-space: nowrap;
  width: 80px;
}
.ms-test-running {
  color: #783887;
}
</style>
