<template>
  <div class="text-container" style="border: 1px #dcdfe6 solid; height: 100%; border-radius: 4px; width: 100%">
    <el-form :model="response" ref="response" label-width="100px">
      <el-collapse-transition>
        <el-tabs v-model="activeName" v-show="isActive" style="margin: 20px">
          <el-tab-pane :label="$t('api_test.definition.request.response_header')" name="headers" class="pane">
            <ms-api-key-value :isShowEnable="false" :suggestions="headerSuggestions" :items="response.headers" />
          </el-tab-pane>
          <el-tab-pane :label="$t('api_test.definition.request.response_body')" name="body" class="pane">
            <ms-api-body
              body-type="response"
              :isReadOnly="false"
              :isShowEnable="false"
              :body="response.body"
              :headers="response.headers" />
          </el-tab-pane>

          <el-tab-pane :label="$t('api_test.definition.request.status_code')" name="status_code" class="pane">
            <ms-api-key-value
              :isShowEnable="false"
              :suggestions="headerSuggestions"
              :items="response.statusCode"
              :unShowSelect="true" />
          </el-tab-pane>
        </el-tabs>
      </el-collapse-transition>
    </el-form>
  </div>
</template>

<script>
import MsAssertionResults from './AssertionResults';
import MsCodeEdit from 'metersphere-frontend/src/components/MsCodeEdit';
import MsDropdown from '@/business/commons/MsDropdown';
import MsApiKeyValue from '../ApiKeyValue';
import { REQUEST_HEADERS } from 'metersphere-frontend/src/utils/constants';
import MsApiBody from '../body/ApiBody';

export default {
  name: 'MsResponseText',
  components: {
    MsDropdown,
    MsCodeEdit,
    MsAssertionResults,
    MsApiKeyValue,
    MsApiBody,
  },

  props: {
    response: Object,
  },

  data() {
    return {
      isActive: true,
      activeName: 'headers',
      headerSuggestions: REQUEST_HEADERS,
    };
  },
  methods: {
    active() {
      this.isActive = !this.isActive;
    },
  },

  mounted() {
    if (!this.response.headers) {
      return;
    }
  },
};
</script>

<style scoped>
.text-container .icon {
  padding: 5px;
}

.text-container .collapse {
  cursor: pointer;
}

.text-container .collapse:hover {
  opacity: 0.8;
}

.text-container .icon.is-active {
  transform: rotate(90deg);
}

.text-container .pane {
  background-color: white;
  padding: 1px 0;
  height: 250px;
  overflow-y: auto;
}

.text-container .pane.cookie {
  padding: 0;
}

pre {
  margin: 0;
}
</style>
