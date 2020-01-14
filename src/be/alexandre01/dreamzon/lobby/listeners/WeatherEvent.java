package be.alexandre01.dreamzon.lobby.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherEvent implements Listener{
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event){
        boolean storm = event.toWeatherState();
        if(storm){
            event.setCancelled(true);
        }

    }
}
