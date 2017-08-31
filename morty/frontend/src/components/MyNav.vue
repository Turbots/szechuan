<template>
  <nav class="level is-mobile" style="margin: 10px">
    <div class="level-item has-text-centered">
      <div>
        <p class="heading">Messages</p>
        <p class="title">{{ nrOfMessages }}</p>
      </div>
    </div>
    <div class="level-item has-text-centered">
      <div>
        <p class="heading">Meeseeks</p>
        <p class="title">{{ nrOfMeeseeks }}</p>
      </div>
    </div>
    <div class="level-item has-text-centered">
      <div>
        <p class="heading">Szechuan Sauce</p>
        <p class="title">{{ nrOfSzechuanFound }}</p>
      </div>
    </div>
  </nav>
</template>

<script>
  export default {
    name: 'nav',
    props: ['nrOfMessages', 'nrOfMeeseeks', 'nrOfSzechuanFound']
  }
</script>
