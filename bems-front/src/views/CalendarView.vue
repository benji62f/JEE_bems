<script>
import axios from "axios";
import CreateEventDialog from "@/components/CreateEventDialog.vue";

export default {
  components: { CreateEventDialog },
  data: () => ({
    focus: "",
    type: "month",
    typeToLabel: {
      month: "Month",
      week: "Week",
      day: "Day",
      "4day": "4 Days"
    },
    selectedEvent: {},
    selectedElement: null,
    selectedOpen: false,
    events: [],
  }),
  mounted() {
    this.$refs.calendar.checkChange();
  },
  methods: {
    viewDay({ date }) {
      this.focus = date;
      this.type = "day";
    },
    getEventColor(event) {
      return event.color;
    },
    setToday() {
      this.focus = "";
    },
    prev() {
      this.$refs.calendar.prev();
    },
    next() {
      this.$refs.calendar.next();
    },
    showEvent({ nativeEvent, event }) {
      const open = () => {
        this.selectedEvent = event;
        this.selectedElement = nativeEvent.target;
        requestAnimationFrame(() =>
          requestAnimationFrame(() => (this.selectedOpen = true))
        );
      };

      if (this.selectedOpen) {
        this.selectedOpen = false;
        requestAnimationFrame(() => requestAnimationFrame(() => open()));
      } else {
        open();
      }

      nativeEvent.stopPropagation();
    },
    updateRange({ start, end }) {
      const events = [];
      axios
        .get(
          `${import.meta.env.VITE_BEMS_API_URL}/api/events?start=${start.date}T00:00:00&end=${end.date}T23:59:59`
        )
        .then((response) => {
          response.data.forEach((event) => {
            events.push({
              id: event.id,
              name: event.label,
              details: event.description,
              start: new Date(event.startDate),
              end: new Date(event.endDate),
              color: event.color,
              timed: true,
            });
          });
          this.events = events;
        });
    },
    deleteEvent(id) {
      axios
        .post(`${import.meta.env.VITE_BEMS_API_URL}/api/events/${id}/delete`)
        .then((response) => {
          if (response.status === 200) {
            this.events = this.events.filter(function (value, index, arr) {
              return value.id !== id;
            });
            this.selectedOpen = false;
          }
        });
    },
  },
};
</script>

<template>
  <v-container>
    <v-row class="fill-height" justify="center">
      <v-col cols="12">
        <v-sheet height="64">
          <v-toolbar flat>
            <v-btn text class="mr-4 btn-kumojin-style" @click="setToday">
              Today
            </v-btn>
            <v-btn fab text small class="mr-4 btn-kumojin-style" @click="prev">
              <v-icon small> mdi-chevron-left</v-icon>
            </v-btn>
            <v-btn fab text small class="mr-4 btn-kumojin-style" @click="next">
              <v-icon small> mdi-chevron-right</v-icon>
            </v-btn>
            <v-toolbar-title v-if="$refs.calendar">
              {{ $refs.calendar.title }}
            </v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn text class="mr-4 btn-kumojin-style" @click="$refs.createEventDialog.showDialog()">
              Create an event
            </v-btn>
            <v-menu bottom right>
              <template v-slot:activator="{ on, attrs }">
                <v-btn text class="btn-kumojin-style" v-bind="attrs" v-on="on">
                  <span>{{ typeToLabel[type] }}</span>
                  <v-icon right> mdi-menu-down</v-icon>
                </v-btn>
              </template>
              <v-list>
                <v-list-item @click="type = 'day'">
                  <v-list-item-title>Day</v-list-item-title>
                </v-list-item>
                <v-list-item @click="type = 'week'">
                  <v-list-item-title>Week</v-list-item-title>
                </v-list-item>
                <v-list-item @click="type = 'month'">
                  <v-list-item-title>Month</v-list-item-title>
                </v-list-item>
                <v-list-item @click="type = '4day'">
                  <v-list-item-title>4 days</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </v-toolbar>
        </v-sheet>
        <v-sheet height="600">
          <v-calendar
            ref="calendar"
            v-model="focus"
            color="primary"
            :events="events"
            :event-color="getEventColor"
            :type="type"
            @click:event="showEvent"
            @click:more="viewDay"
            @click:date="viewDay"
            @change="updateRange"
          ></v-calendar>
          <v-menu
            v-model="selectedOpen"
            :close-on-content-click="false"
            :activator="selectedElement"
            offset-x
          >
            <v-card color="grey lighten-4" min-width="350px" flat>
              <v-toolbar :color="selectedEvent.color" dark>
                <v-icon class="mr-4">mdi-calendar</v-icon>
                <v-toolbar-title class="mr-4">{{ selectedEvent.name }}</v-toolbar-title>
                <v-spacer></v-spacer>
                <v-btn icon @click="$refs.createEventDialog.showDialog(selectedEvent)">
                  <v-icon>mdi-pencil</v-icon>
                </v-btn>
                <v-btn icon @click="deleteEvent(selectedEvent.id)">
                  <v-icon>mdi-delete</v-icon>
                </v-btn>
              </v-toolbar>
              <v-card-text>
                <v-icon class="mr-2">mdi-alarm</v-icon>
                <span>{{ selectedEvent.start | formatDate }}</span> to
                <span>{{ selectedEvent.end | formatDate }}</span>
                <div v-if="selectedEvent.details">
                  <br />
                  <v-icon class="mr-2">mdi-format-list-bulleted</v-icon>
                  <span>{{ selectedEvent.details }}</span>
                </div>
              </v-card-text>
              <v-card-actions>
                <v-btn text color="secondary" @click="selectedOpen = false">
                  Close
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-menu>
        </v-sheet>
      </v-col>
    </v-row>
    <CreateEventDialog ref="createEventDialog" />
  </v-container>
</template>
