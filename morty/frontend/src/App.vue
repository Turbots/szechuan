<template>
  <div>
    <my-header></my-header>
    <my-nav :nrOfMessages="nrOfMessages" :nrOfMeeseeks="nrOfMeeseeks" :nrOfSzechuanFound="nrOfSzechuanFound"></my-nav>
    <div class="tile is-ancestor">
      <div class="tile is-4 is-vertical is-parent">
        <article class="tile is-child notification is-success">
          <p class="title">Rick</p>
          <p class="subtitle"><i>I want that Mulan McNugget sauce, Morty. That's my series arc! If it takes nine
            seasons!</i></p>
          <figure class="image is-4by3">
            <img src="img/rick.gif">
          </figure>
        </article>
      </div>
      <div class="tile is-4 is-vertical is-parent">
        <article class="tile is-child notification is-info">
          <p class="title">Meeseeks</p>
          <p class="subtitle">Meeseeks are creatures created to serve a singular purpose for which they will go to
            any length to fulfill.
            After they serve their purpose, they expire and vanish into the air.</p>
          <figure class="image is-4by3">
            <img src="img/meeseeks.gif">
          </figure>
        </article>
      </div>
      <div class="tile is-4 is-vertical is-parent">
        <article class="tile is-child notification is-danger">
          <p class="title">McDonalds</p>
          <p class="subtitle">Szechuan Chicken McNugget Sauce was a dipping sauce for McDonald's Chicken McNuggets.
            Offered as a tie-in for the movie Mulan, the sauce was only available in 1998.</p>
          <figure class="image is-4by3">
            <img src="img/mcdonalds.gif">
          </figure>
        </article>
      </div>
    </div>
    <div class="tile is-ancestor">
      <div class="tile is-4 is-vertical is-parent">
        <article class="tile is-child is-success">
          <table class="table is-fullwidth is-striped">
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
        </article>
      </div>
      <div class="tile is-4 is-vertical is-parent">
        <article class="tile is-child is-success">
          <table class="table is-fullwidth is-striped">
            <thead>
            <tr>
              <th>Time</th>
              <th>Message</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="item in meeseeks">
              <td>{{ item.time }}</td>
              <td>{{ item.message }}</td>
            </tr>
            </tbody>
          </table>
        </article>
      </div>
      <div class="tile is-4 is-vertical is-parent">
        <article class="tile is-child is-success">
          <table class="table is-fullwidth is-striped">
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
        </article>
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
              message: data.quote.message
            })
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
        if (array.length > 10) {
          array.shift()
        }
        array.push(message)
      }
    }
  }
</script>

<style src="./assets/bulma.min.css"></style>
