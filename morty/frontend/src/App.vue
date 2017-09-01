<template>
  <div>
    <my-header></my-header>
    <my-nav :nrOfMessages="nrOfMessages" :nrOfMeeseeks="nrOfMeeseeks" :nrOfSzechuanFound="nrOfSzechuanFound"></my-nav>
    <audio ref="szechuanSauce" src="sounds/szechuan-sauce.mp3"></audio>
    <audio ref="mrMeeseeksSpawn" src="sounds/mr-meeseeks-spawn.mp3"></audio>
    <audio ref="imMrMeeseeks1" src="sounds/mr-meeseeks-1.mp3"></audio>
    <audio ref="imMrMeeseeks2" src="sounds/mr-meeseeks-2.mp3"></audio>
    <audio ref="imMrMeeseeks3" src="sounds/mr-meeseeks-3.wav"></audio>
    <audio ref="wubbaLubbaDubDub" src="sounds/wubba-lubba-dub-dub.wav"></audio>
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
          <figure class="image overlay-container" v-on:click="startSearching">
            <img src="img/rick.gif">
            <div class="overlay">
              <div class="search-text">Get That Szechuan Sauce!</div>
            </div>
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
          <figure class="image overlay-container" v-on:click="spawnMeeseeks">
            <img src="img/meeseeks.gif">
            <div class="overlay">
              <div class="search-text">Press The Meeseeks Box!</div>
            </div>
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
  import axios from 'axios'

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
            if (data.quote.message === 'IM_MR_MEESEEKS_LOOK_AT_ME') {
              this.$refs.imMrMeeseeks1.play()
            } else if (data.quote.message === 'HEY_THERE_IM_MR_MEESEEKS') {
              this.$refs.imMrMeeseeks2.play()
            } else if (data.quote.message === 'LOOK_AT_ME_IM_MR_MEESEEKS') {
              this.$refs.imMrMeeseeks3.play()
            }
            this.addMessage(this.meeseeks, {
              time: time,
              instance: data.instanceIndex,
              message: data.quote.message
            })
            this.nrOfMeeseeks = Math.max.apply(Math, this.meeseeks.map(function (item) {
              return parseInt(item.instance) + 1
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
            this.$refs.wubbaLubbaDubDub.play()
            this.terminateMeeseeks()
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
      },
      startSearching () {
        this.$refs.szechuanSauce.play()
        axios.get('https://rnm-rick.cfapps.io/').catch(function (error) {
          console.log(error.message)
        })
      },
      spawnMeeseeks () {
        this.$refs.mrMeeseeksSpawn.play()
        axios.post('https://rnm-meeseeks-box.cfapps.io/').catch(function (error) {
          console.log(error.message)
        })
      },
      terminateMeeseeks () {
        axios.delete('https://rnm-meeseeks-box.cfapps.io/').catch(function (error) {
          console.log(error.message)
        })
      }
    }
  }
</script>

<style src="./assets/bulma.min.css"></style>

<style type="text/css">
  .overlay {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    height: 100%;
    width: 100%;
    opacity: 0;
    transition: .5s ease;
    background-color: #008CBA;
  }

  .overlay-container:hover .overlay {
    opacity: 0.5;
    cursor: pointer;
  }

  .search-text {
    color: white;
    font-size: 20px;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    -ms-transform: translate(-50%, -50%);
  }
</style>
