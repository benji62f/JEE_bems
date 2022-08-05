<script>
import moment from "moment-timezone";

export default {
  data: () => ({
    dialog: Boolean,
    todayDate: Date,
  }),
  methods: {
    showDialog() {
      this.dialog = true;
    },
  },
  mounted() {
    this.dialog = false;
    this.todayDate = moment(new Date()).format("YYYY-MM-DDTHH:mm");
  },
};
</script>

<template>
  <v-row justify="center">
    <v-dialog
      v-model="dialog"
      persistent
      max-width="600px"
    >
      <v-card>
        <v-card-title>
          <span class="text-h5">Create an event</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  label="Label*"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  label="Description"
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <input type="datetime-local" :value="todayDate" />
              </v-col>
              <v-col cols="12">
                <input type="datetime-local" :value="todayDate"/>
              </v-col>
            </v-row>
          </v-container>
          <small>*indicates required field</small>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="blue darken-1"
            text
            @click="dialog = false"
          >
            Close
          </v-btn>
          <v-btn
            color="blue darken-1"
            text
            @click="dialog = false"
          >
            Save
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>