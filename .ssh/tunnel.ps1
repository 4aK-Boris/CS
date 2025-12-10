# tunnel.ps1
$key = "id_rsa"

# ADB forward
Write-Host "Setting up ADB forward..."
adb -s e3e65dbd forward tcp:1070 tcp:1070
adb -s 82bc3269 forward tcp:1069 tcp:1069
adb -s 4e5a1437 forward tcp:1068 tcp:1068
Write-Host "ADB forward done"

# Port proxy
Write-Host "Setting up port proxy..."
netsh interface portproxy delete v4tov4 listenport=1070 listenaddress=0.0.0.0 2>$null
netsh interface portproxy delete v4tov4 listenport=1069 listenaddress=0.0.0.0 2>$null
netsh interface portproxy delete v4tov4 listenport=1068 listenaddress=0.0.0.0 2>$null

netsh interface portproxy add v4tov4 listenport=1070 listenaddress=0.0.0.0 connectport=1070 connectaddress=127.0.0.1
netsh interface portproxy add v4tov4 listenport=1069 listenaddress=0.0.0.0 connectport=1069 connectaddress=127.0.0.1
netsh interface portproxy add v4tov4 listenport=1068 listenaddress=0.0.0.0 connectport=1068 connectaddress=127.0.0.1
Write-Host "Port proxy done"

# Firewall
Write-Host "Setting up firewall..."
netsh advfirewall firewall delete rule name="Mobile Proxy 1070" 2>$null
netsh advfirewall firewall delete rule name="Mobile Proxy 1069" 2>$null
netsh advfirewall firewall delete rule name="Mobile Proxy 1068" 2>$null
netsh advfirewall firewall delete rule name="Mobile Proxy Control" 2>$null

netsh advfirewall firewall add rule name="Mobile Proxy 1070" dir=in action=allow protocol=tcp localport=1070
netsh advfirewall firewall add rule name="Mobile Proxy 1069" dir=in action=allow protocol=tcp localport=1069
netsh advfirewall firewall add rule name="Mobile Proxy 1068" dir=in action=allow protocol=tcp localport=1068
netsh advfirewall firewall add rule name="Mobile Proxy Control" dir=in action=allow protocol=tcp localport=8090
Write-Host "Firewall done"

# SSH tunnel with auto-reconnect
while ($true) {
    Write-Host "$(Get-Date) - Connecting to VPS..."
    ssh -i $key -N `
        -o "ServerAliveInterval=30" `
        -o "ServerAliveCountMax=3" `
        -o "ConnectTimeout=10" `
        -o "TCPKeepAlive=yes" `
        -R 8090:127.0.0.1:8090 `
        -R 1070:127.0.0.1:1070 `
        -R 1069:127.0.0.1:1069 `
        -R 1068:127.0.0.1:1068 `
        root@77.222.38.54
    Write-Host "$(Get-Date) - Disconnected, reconnecting in 5 sec..."

    # Re-setup ADB forward
    adb -s e3e65dbd forward tcp:1070 tcp:1070
    adb -s 82bc3269 forward tcp:1069 tcp:1069
    adb -s 4e5a1437 forward tcp:1068 tcp:1068

    Start-Sleep -Seconds 5
}