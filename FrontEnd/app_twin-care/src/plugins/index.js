/**
 * Inclusion automatique dans ./src/main.js
 */
import vuetify from './vuetify'

export function registerPlugins (app) {
  app.use(vuetify)
}