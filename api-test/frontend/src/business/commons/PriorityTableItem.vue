<template>
  <div>
    <ms-tag v-if="value === 'P0'" type="danger" :content="value" />
    <ms-tag v-else-if="value === 'P1'" type="warning" :content="value" />
    <ms-tag v-else-if="value === 'P2'" effect="light" type="danger" :content="value" />
    <ms-tag v-else-if="value === 'P3'" effect="light" type="warning" :content="value" />
    <ms-tag v-else-if="value" :content="value" />
  </div>
</template>

<script>
import MsTag from 'metersphere-frontend/src/components/MsTag';

export default {
  name: 'PriorityTableItem',
  components: { MsTag },
  props: {
    value: {
      type: String,
    },
  },
};
</script>

<style scoped></style>
