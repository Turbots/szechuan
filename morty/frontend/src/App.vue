<template>
  <div>
    <my-header></my-header>
    <my-nav :nrOfMessages="nrOfMessages" :nrOfMeeseeks="nrOfMeeseeks" :nrOfSzechuanFound="nrOfSzechuanFound"></my-nav>
    <div class="columns">
      <div class="column">
        <table class="table is-fullwidth is-striped is-narrow">
          <thead>
          <tr>
            <th>Time</th>
            <th>Message</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="item in rick">
            <td>{{ item.time }}</td>
            <td>{{ item.message }}</td>
          </tr>
          </tbody>
        </table>
      </div>
      <div class="column">
        <table class="table is-fullwidth is-striped is-narrow">
          <thead>
          <tr>
            <th>Time</th>
            <th>Instance</th>
            <th>Message</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="item in meeseeks">
            <td>{{ item.time }}</td>
            <td>{{ item.instance }}</td>
            <td>{{ item.message }}</td>
          </tr>
          </tbody>
        </table>
      </div>
      <div class="column">
        <table class="table is-fullwidth is-striped is-narrow">
          <thead>
          <tr>
            <th>Time</th>
            <th>Message</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="item in mcdonalds">
            <td>{{ item.time }}</td>
            <td>{{ item.message }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="columns is-mobile is-multiline">
      <div class="column">
        <div class="box notification is-success">
          <article style="padding: 1em;">
            <p class="title">Rick</p>
            <p class="subtitle"><i>Rick Sanchez is a genius scientist whose alcoholism and reckless, nihilistic behavior are a source of concern for his daughter's family</i>
            </p>
          </article>
          <figure class="image">
            <img src="img/rick.gif">
          </figure>
        </div>
      </div>
      <div class="column">
        <div class="box notification is-warning">
          <article style="padding: 1em;">
            <p class="title">Meeseeks</p>
            <p class="subtitle"><i>Meeseeks are creatures created to serve a singular purpose for which they will go to any length to fulfill. After they serve their purpose, they expire and vanish into the air.</i>
            </p>
          </article>
          <figure class="image">
            <img src="img/meeseeks.gif">
          </figure>
        </div>
      </div>
      <div class="column">
        <div class="box notification is-danger">
          <article style="padding: 1em;">
            <p class="title">McDonalds</p>
            <p class="subtitle"><i>Szechuan Chicken McNugget Sauce was a dipping sauce for McDonald's Chicken McNuggets. The sauce was only available in 1998.</i>
            </p>
          </article>
          <figure class="image">
            <img src="img/mcdonalds.gif">
          </figure>
        </div>
      </div>
    </div>
    <my-footer></my-footer>
  </div>
</template>

<script>
  import MyHeader from './components/MyHeader'
  import MyNav from './components/MyNav'
  import MyFooter from './components/MyFooter'

  export default {
    name: 'app',
    components: {
      MyHeader, MyNav, MyFooter
    },
    data: function () {
      return {
        rick: [],
        meeseeks: [],
        mcdonalds: [],
        nrOfMessages: 0,
        nrOfMeeseeks: 0,
        nrOfSzechuanFound: 0
      }
    },
    created () {
      this.setupStream()
    },
    methods: {
      setupStream () {
        let es = new EventSource('/events')

        es.addEventListener('message', event => {
          let data = JSON.parse(event.data)
          let d = new Date()
          let time = d.toLocaleTimeString()
          this.nrOfMessages++

          if (data.quote.author === 'MR_MEESEEKS') {
            this.addMessage(this.meeseeks, {
              time: time,
              instance: data.instanceIndex,
              message: data.quote.message
            })
            this.nrOfMeeseeks = Math.max.apply(Math, this.meeseeks.map(function (item) {
              return item.instance + 1
            }))
          } else if (data.quote.author === 'RICK') {
            this.addMessage(this.rick, {
              time: time,
              message: data.quote.message
            })
          } else if (data.quote.author === 'MCDONALDS') {
            this.addMessage(this.mcdonalds, {
              time: time,
              message: data.quote.message
            })
          }

          if (data.quote.message === 'YOU_ARE_A_WINNER') {
            this.nrOfSzechuanFound++
          }
        }, false)

        es.addEventListener('error', event => {
          if (event.readyState === EventSource.CLOSED) {
            console.log('Event was closed')
            console.log(EventSource)
          }
        }, false)
      },
      addMessage (array, message) {
        if (array.length >= 8) {
          array.shift()
        }
        array.push(message)
      }
    }
  }
</script>

<style src="./assets/bulma.min.css"></style>
