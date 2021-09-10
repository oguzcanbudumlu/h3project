import React from "react";
import './App.css';
import maplibre from 'maplibre-gl';

maplibre.accessToken = 'pk.eyJ1IjoiaWJyYWhpbXNhcmljaWNlayIsImEiOiJjOTY5MzE2YzVkMmY4ZjkxNDZiOWNkMGQ4MDJiZTE3MCJ9.BnJGGOoXAdwicB9shSV_Qg';

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            lng: 35,
            lat: 39,
            zoom: 5
        }

        this.mapContainer = React.createRef();
    }

    componentDidMount() {
        const { lng, lat, zoom } = this.state;
        const map = new maplibre.Map({
            container: this.mapContainer.current,
            style: 'mapbox://style/mapbox/dark-v10',
            center: [lng, lat],
            zoom: zoom
        });

        this.map = map;
    }

    render() {
        return (
            <div ref={this.mapContainer}>
            </div>
        );
    }
}

export default App;
