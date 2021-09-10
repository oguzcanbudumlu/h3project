import React from "react";
import './App.css';
import maplibre from 'maplibre-gl';
import 'maplibre-gl/dist/maplibre-gl.css';

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
        this.setMoveEvent();
    }

    render() {
        return (
            <div ref={this.mapContainer} className="map-container">
            </div>
        );
    }

    setMoveEvent() {
        this.map.on('move', () => {
            this.setState({
                lng: this.map.getCenter().lng.toFixed(4),
                lat: this.map.getCenter().lat.toFixed(4),
                zoom: this.map.getZoom().toFixed(2)
            })
        });
    }
}

export default App;
